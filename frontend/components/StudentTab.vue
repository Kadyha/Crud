<template>
  <div>
    <form @submit.prevent="validateAndSaveStudent" class="mb-3">
      <input type="hidden" v-model="student.id" />
      <div v-if="error" class="alert alert-danger py-1">{{ error }}</div>
      <div class="row g-2 mb-2">
        <div class="col-md-2">
          <input v-model="student.firstName" class="form-control" placeholder="First Name *" />
        </div>
        <div class="col-md-2">
          <input v-model="student.lastName" class="form-control" placeholder="Last Name *" />
        </div>
        <div class="col-md-2">
          <input v-model="student.email" class="form-control" placeholder="Email *" />
        </div>
        <div class="col-md-2">
          <input v-model="student.phone" type="number" class="form-control" placeholder="Phone" min="0" />
        </div>
        <div class="col-md-2">
          <input v-model="student.studentNumber" type="number" class="form-control" placeholder="Student Number *" min="0" />
        </div>
        <div class="col-md-2">
          <input v-model="student.averageMark" type="number" class="form-control" placeholder="Average Mark *" />
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
      :address="student.address || {}"
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
            <th>Student Number</th>
            <th>Average Mark</th>
            <th>Address</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="s in students" :key="s.id">
            <td>{{ s.firstName }}</td>
            <td>{{ s.lastName }}</td>
            <td>{{ s.email }}</td>
            <td>{{ s.phone }}</td>
            <td>{{ s.studentNumber }}</td>
            <td>{{ s.averageMark }}</td>
            <td>{{ formatAddress(s.address) }}</td>
            <td>
              <button class="btn btn-sm btn-warning me-1" @click="editStudent(s)">Edit</button>
              <button class="btn btn-sm btn-danger" @click="deleteStudent(s.id)">Delete</button>
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
const apiUrl = '/api/students';
const students = ref([]);
const student = ref({ firstName: '', lastName: '', email: '', phone: '', studentNumber: '', averageMark: '', address: {} });
const error = ref('');
function fetchStudents() {
  fetch(apiUrl).then(res => res.json()).then(data => students.value = data);
}
onMounted(fetchStudents);
function validateAndSaveStudent() {
  error.value = '';
  if (!student.value.firstName || !student.value.lastName || !student.value.email || !student.value.studentNumber || student.value.averageMark === '' || student.value.averageMark === null) {
    error.value = 'First name, last name, email, student number, and average mark are required.';
    return;
  }
  if (student.value.phone && (isNaN(student.value.phone) || Number(student.value.phone) < 0)) {
    error.value = 'Phone number must be a non-negative number.';
    return;
  }
  if (isNaN(student.value.studentNumber) || Number(student.value.studentNumber) < 0) {
    error.value = 'Student number must be a non-negative number.';
    return;
  }
  saveStudent();
}
function saveStudent() {
  const method = student.value.id ? 'PUT' : 'POST';
  const url = student.value.id ? `${apiUrl}/${student.value.id}` : apiUrl;
  const payload = { ...student.value };
  if (payload.address && Object.values(payload.address).every(v => !v)) {
    delete payload.address;
  }
  fetch(url, {
    method,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  }).then(() => {
    student.value = { firstName: '', lastName: '', email: '', phone: '', studentNumber: '', averageMark: '', address: {} };
    fetchStudents();
  });
}
function editStudent(s) {
  student.value = { ...s };
}
function deleteStudent(id) {
  fetch(`${apiUrl}/${id}`, { method: 'DELETE' }).then(fetchStudents);
}
function onAddressSave(address) {
  student.value.address = address;
  showAddressModal.value = false;
}
function formatAddress(addr) {
  if (!addr || Object.values(addr).every(v => !v)) return '';
  return [addr.street, addr.city, addr.state, addr.postalCode, addr.country].filter(Boolean).join(', ');
}
const addressLabel = computed(() => {
  return formatAddress(student.value.address) || 'Set Address';
});
</script>
