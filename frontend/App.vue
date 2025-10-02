<template>
  <div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h1 class="mb-0">CRUD Management (Vue)</h1>
      <div>
        <span v-if="session.authenticated" class="me-3">👤 {{ session.name }}</span>
        <a v-if="!session.authenticated" :href="loginHref" class="btn btn-primary btn-sm me-2">Login</a>
        <button v-else @click="logout" class="btn btn-outline-secondary btn-sm">Logout</button>
      </div>
    </div>
    <div v-if="loginError" class="alert alert-danger" role="alert">
      Hubo un problema iniciando sesión con GitHub. Inténtalo de nuevo.
    </div>
    <template v-if="session.authenticated">
      <ul class="nav nav-tabs mb-3">
        <li class="nav-item" v-for="tab in tabs" :key="tab.id">
          <a class="nav-link" :class="{active: activeTab === tab.id}" href="#" @click.prevent="activeTab = tab.id">{{ tab.label }}</a>
        </li>
      </ul>
      <div class="card p-4 mb-4">
        <component :is="activeTabComponent" @address-modal="openAddressModal" @update-address="updateAddress" />
      </div>
      <AddressModal v-if="showAddressModal" :address="modalAddress" @save="saveAddress" @close="showAddressModal = false" />
    </template>
    <template v-else>
      <div class="card p-4 text-center">
        <p class="mb-3">Redirigiendo al login…</p>
        <a :href="loginHref" class="btn btn-primary">Ir al login</a>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import PersonTab from './components/PersonTab.vue';
import StudentTab from './components/StudentTab.vue';
import ProfessorTab from './components/ProfessorTab.vue';
import AddressModal from './components/AddressModal.vue';

const tabs = [
  { id: 'person', label: 'Persons', component: PersonTab },
  { id: 'student', label: 'Students', component: StudentTab },
  { id: 'professor', label: 'Professors', component: ProfessorTab }
];
const activeTab = ref('person');
const showAddressModal = ref(false);
const modalAddress = ref({});
let addressCallback = null;

const session = ref({ authenticated: false, name: '' });
const loginError = ref(false);
// Fallback a mismo origen con proxy Nginx si no hay VITE_API_URL
const apiBase = import.meta.env.VITE_API_URL || '/api';
const backendBase = apiBase && /^https?:\/\//.test(apiBase) ? apiBase.replace(/\/api\/?$/, '') : '';
const isBrowser = typeof window !== 'undefined';
const isLocalHost = isBrowser && (window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1');
const isDockerLocal = isBrowser && isLocalHost;
const authProvider = import.meta.env.VITE_AUTH_PROVIDER || 'local'; // 'local' | 'oauth'
// En Vercel (VITE_API_URL presente) usar URL absoluta al backend; en Docker/local usar rutas relativas para proxy Nginx
// Normalize login target:
// - Docker/local: force same-origin and, if running on localhost with a non-5173 port, normalize to localhost:5173
// - Cloud (Vercel): use absolute backend when VITE_API_URL provided
// Single login page (Spring Security default), always same-origin
let loginHref = '/login';
if (!backendBase && isLocalHost && isBrowser && window.location.port && window.location.port !== '5173') {
  // Normalizar a 5173 en local si no usamos backendBase
  loginHref = `${window.location.protocol}//localhost:5173/login`;
}

function fetchSession() {
  return fetch(`${apiBase}/auth/me`, { credentials: 'include' })
    .then(r => r.ok ? r.json() : { authenticated: false })
    .then(data => { session.value = data; return data; })
    .catch(() => ({ authenticated: false }));
}
function logout() {
  // Invalidate session cookie by hitting Spring Security logout (same-origin, proxied por Nginx/Vercel)
  const logoutUrl = '/logout';
  fetch(logoutUrl, { method: 'POST', credentials: 'include' })
    .finally(() => {
      session.value = { authenticated: false, name: '' };
      window.location.href = loginHref;
    });
}

function openAddressModal(address, callback) {
  modalAddress.value = { ...address };
  showAddressModal.value = true;
  addressCallback = callback;
}
function saveAddress(address) {
  showAddressModal.value = false;
  if (addressCallback) {
    addressCallback(address);
    addressCallback = null;
  }
}
function updateAddress(address) {
  // This is a placeholder for future logic if needed
}
const activeTabComponent = computed(() => {
  const tab = tabs.find(t => t.id === activeTab.value);
  return tab ? tab.component : null;
});

onMounted(async () => {
  const params = new URLSearchParams(window.location.search);
  if (params.get('login') === 'error') {
    loginError.value = true;
    // limpiar el query param para que no persista al navegar
    const url = new URL(window.location.href);
    url.searchParams.delete('login');
    window.history.replaceState({}, '', url.toString());
  }
  const data = await fetchSession();
  // Si no está autenticado, redirigir directamente a la página de login única
  if (!data.authenticated) {
    window.location.href = loginHref;
  }
});
</script>

<style>
@import 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css';

body {
  background: #f8f9fa;
}
.card {
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}
.nav-tabs .nav-link.active {
  background: #007bff;
  color: #fff;
}
.table th, .table td {
  vertical-align: middle;
}
</style>
