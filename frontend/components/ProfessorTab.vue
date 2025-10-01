<template>
  <div>
    <form @submit.prevent="validateAndSaveProfessor" class="mb-3">
      <input type="hidden" v-model="professor.id" />
      <div v-if="error" class="alert alert-danger py-1">{{ error }}</div>
      <div class="row g-2 mb-2">
        <div class="col-md-2">
          <input v-model="professor.firstName" class="form-control" placeholder="First Name *" />
        </div>
        <div class="col-md-2">
          <input v-model="professor.lastName" class="form-control" placeholder="Last Name *" />
        </div>
        <div class="col-md-2">
          <input v-model="professor.email" class="form-control" placeholder="Email *" />
        </div>
        <div class="col-md-2">
          <input v-model="professor.phone" type="number" class="form-control" placeholder="Phone" min="0" />
        </div>
        <!-- Removed department field -->
        <div class="col-md-2">
          <input v-model="professor.salary" type="number" class="form-control" placeholder="Salary" />
        </div>
        <div class="col-md-2">
          <button type="button" class="btn btn-secondary w-100" @click="showAddressModal = true">{{ addressLabel }}</button>
        </div>
      </div>
      <div class="row mb-2">
        <div class="col-md-2"><button type="submit" class="btn btn-primary w-100">Save</button></div>
      </div>
    </form>
    <AddressModal
      v-if="showAddressModal"
      :address="professor.address || {}"
      @save="onAddressSave"
      @close="showAddressModal = false"
    />
    <div class="table-responsive">
      <table class="table table-bordered table-striped">
        <thead class="table-light">
          <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Phone</th>
            <!-- Removed department column -->
            <th>Salary</th>
            <th>Address</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="p in professors" :key="p.id">
            <td>{{ p.firstName }}</td>
            <td>{{ p.lastName }}</td>
            <td>{{ p.email }}</td>
            <td>{{ p.phone }}</td>
            <!-- Removed department cell -->
            <td>{{ p.salary }}</td>
            <td>{{ formatAddress(p.address) }}</td>
            <td>
              <button class="btn btn-sm btn-warning me-1" @click="editProfessor(p)">Edit</button>
              <button class="btn btn-sm btn-danger" @click="deleteProfessor(p.id)">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import AddressModal from './AddressModal.vue';
const showAddressModal = ref(false);
const apiUrl = '/api/professors';
const professors = ref([]);
const professor = ref({ firstName: '', lastName: '', email: '', phone: '', salary: '', address: {} });
const error = ref('');
function fetchProfessors() {
  fetch(apiUrl).then(res => res.json()).then(data => professors.value = data);
}
onMounted(fetchProfessors);
function validateAndSaveProfessor() {
  error.value = '';
  if (!professor.value.firstName || !professor.value.lastName || !professor.value.email) {
    error.value = 'First name, last name, and email are required.';
    return;
  }
  if (professor.value.phone && (isNaN(professor.value.phone) || Number(professor.value.phone) < 0)) {
    error.value = 'Phone number must be a non-negative number.';
    return;
  }
  saveProfessor();
}
function saveProfessor() {
  const method = professor.value.id ? 'PUT' : 'POST';
  const url = professor.value.id ? `${apiUrl}/${professor.value.id}` : apiUrl;
  const payload = { ...professor.value };
  if (payload.address && Object.values(payload.address).every(v => !v)) {
    delete payload.address;
  }
  fetch(url, {
    method,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  }).then(() => {
    professor.value = { firstName: '', lastName: '', email: '', phone: '', salary: '', address: {} };
    fetchProfessors();
  });
}
function editProfessor(p) {
  professor.value = { ...p };
}
function deleteProfessor(id) {
  fetch(`${apiUrl}/${id}`, { method: 'DELETE' }).then(fetchProfessors);
}
function onAddressSave(address) {
  professor.value.address = address;
  showAddressModal.value = false;
}
function formatAddress(addr) {
  if (!addr || Object.values(addr).every(v => !v)) return '';
  return [addr.street, addr.city, addr.state, addr.postalCode, addr.country].filter(Boolean).join(', ');
}
const addressLabel = computed(() => {
  return formatAddress(professor.value.address) || 'Set Address';
});
</script>
