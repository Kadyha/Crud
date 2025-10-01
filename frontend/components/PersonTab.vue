<template>
  <div>
    <form @submit.prevent="validateAndSavePerson" class="mb-3">
      <input type="hidden" v-model="person.id" />
      <div v-if="error" class="alert alert-danger py-1">{{ error }}</div>
      <div class="row g-2 mb-2">
        <div class="col-md-2">
          <input v-model="person.firstName" class="form-control" placeholder="First Name *" />
        </div>
        <div class="col-md-2">
          <input v-model="person.lastName" class="form-control" placeholder="Last Name *" />
        </div>
        <div class="col-md-2">
          <input v-model="person.email" class="form-control" placeholder="Email *" />
        </div>
        <div class="col-md-2">
          <input v-model="person.phone" type="number" class="form-control" placeholder="Phone" min="0" />
        </div>
        <div class="col-md-2">
          <button type="button" class="btn btn-secondary w-100" @click="showAddressModal = true">{{ addressLabel }}</button>
        </div>
        <div class="col-md-2"><button type="submit" class="btn btn-primary w-100">Save</button></div>
      </div>
    </form>
    <AddressModal
      v-if="showAddressModal"
      :address="person.address || {}"
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
            <th>Address</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="p in persons" :key="p.id">
            <td>{{ p.firstName }}</td>
            <td>{{ p.lastName }}</td>
            <td>{{ p.email }}</td>
            <td>{{ p.phone }}</td>
            <td>{{ formatAddress(p.address) }}</td>
            <td>
              <button class="btn btn-sm btn-warning me-1" @click="editPerson(p)">Edit</button>
              <button class="btn btn-sm btn-danger" @click="deletePerson(p.id)">Delete</button>
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
const apiUrl = `${import.meta.env.VITE_API_URL}/persons`;
const persons = ref([]);
const person = ref({ firstName: '', lastName: '', email: '', phone: '', address: {} });
const error = ref('');
function fetchPersons() {
  fetch(apiUrl).then(res => res.json()).then(data => persons.value = data);
}
onMounted(fetchPersons);
function validateAndSavePerson() {
  error.value = '';
  if (!person.value.firstName || !person.value.lastName || !person.value.email) {
    error.value = 'First name, last name, and email are required.';
    return;
  }
  if (person.value.phone && (isNaN(person.value.phone) || Number(person.value.phone) < 0)) {
    error.value = 'Phone number must be a non-negative number.';
    return;
  }
  savePerson();
}
function savePerson() {
  const method = person.value.id ? 'PUT' : 'POST';
  const url = person.value.id ? `${apiUrl}/${person.value.id}` : apiUrl;
  // Only include address if at least one field is filled
  const payload = { ...person.value };
  if (payload.address && Object.values(payload.address).every(v => !v)) {
    delete payload.address;
  }
  fetch(url, {
    method,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  }).then(() => {
    person.value = { firstName: '', lastName: '', email: '', phone: '', address: {} };
    fetchPersons();
  });
}
function editPerson(p) {
  person.value = { ...p };
}
function deletePerson(id) {
  fetch(`${apiUrl}/${id}`, { method: 'DELETE' }).then(fetchPersons);
}
function onAddressSave(address) {
  person.value.address = address;
  showAddressModal.value = false;
}
function formatAddress(addr) {
  if (!addr || Object.values(addr).every(v => !v)) return '';
  return [addr.street, addr.city, addr.state, addr.postalCode, addr.country].filter(Boolean).join(', ');
}
const addressLabel = computed(() => {
  return formatAddress(person.value.address) || 'Set Address';
});
</script>
