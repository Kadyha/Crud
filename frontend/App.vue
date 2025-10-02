<template>
  <div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h1 class="mb-0">Person Management (Vue)</h1>
      <div>
        <span v-if="session.authenticated" class="me-3">👤 {{ session.name }}</span>
        <a v-if="!session.authenticated" :href="loginUrl" class="btn btn-primary btn-sm me-2">Login</a>
        <button v-else @click="logout" class="btn btn-outline-secondary btn-sm">Logout</button>
      </div>
    </div>
    <ul class="nav nav-tabs mb-3">
      <li class="nav-item" v-for="tab in tabs" :key="tab.id">
        <a class="nav-link" :class="{active: activeTab === tab.id}" href="#" @click.prevent="activeTab = tab.id">{{ tab.label }}</a>
      </li>
    </ul>
    <div class="card p-4 mb-4">
      <component :is="activeTabComponent" @address-modal="openAddressModal" @update-address="updateAddress" />
    </div>
  <AddressModal v-if="showAddressModal" :address="modalAddress" @save="saveAddress" @close="showAddressModal = false" />
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
const apiBase = import.meta.env.VITE_API_URL;
const backendBase = apiBase?.replace(/\/api\/?$/, '') || '';
const authProvider = import.meta.env.VITE_AUTH_PROVIDER || 'local'; // 'local' | 'oauth'
const loginUrl = authProvider === 'oauth'
  ? `${backendBase}/oauth2/authorization/github`
  : `${backendBase}/login`;

function fetchSession() {
  return fetch(`${apiBase}/auth/me`, { credentials: 'include' })
    .then(r => r.ok ? r.json() : { authenticated: false })
    .then(data => { session.value = data; return data; })
    .catch(() => ({ authenticated: false }));
}
function logout() {
  // Invalidate session cookie by hitting Spring Security logout
  fetch(`${backendBase}/logout`, { method: 'POST', credentials: 'include' })
    .finally(() => { session.value = { authenticated: false, name: '' }; });
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
  const data = await fetchSession();
  // In local mode, if not authenticated, send user to the local login page
  if (authProvider === 'local' && !data.authenticated) {
    window.location.href = `${backendBase}/login`;
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
