<template>
  <div class="container mt-4">
    <h1 class="mb-4">Person Management (Vue) 3</h1>
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
import { ref, computed } from 'vue';
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
