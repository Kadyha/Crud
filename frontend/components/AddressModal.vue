<template>
  <div class="modal fade show" tabindex="-1" style="display: block; background: rgba(0,0,0,0.3);">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Edit Address</h5>
          <button type="button" class="btn-close" @click="close"></button>
        </div>
        <form @submit.prevent="validateAndSave">
          <div class="modal-body">
            <div v-if="error" class="alert alert-danger py-1">{{ error }}</div>
            <div class="mb-2">
              <input v-model="address.street" class="form-control" placeholder="Street *" />
            </div>
            <div class="mb-2">
              <input v-model="address.city" class="form-control" placeholder="City *" />
            </div>
            <div class="mb-2">
              <input v-model="address.state" class="form-control" placeholder="State *" />
            </div>
            <div class="mb-2">
              <input v-model="address.postalCode" class="form-control" placeholder="Postal Code *" />
            </div>
            <div class="mb-2">
              <input v-model="address.country" class="form-control" placeholder="Country *" />
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="close">Cancel</button>
            <button type="submit" class="btn btn-primary">Save</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, defineProps, defineEmits } from 'vue';
const props = defineProps({
  address: Object,
});
const emit = defineEmits(['save', 'close']);
const address = ref({ ...props.address });
const error = ref('');
watch(() => props.address, v => address.value = { ...v });
function close() {
  emit('close');
}
function validateAndSave() {
  error.value = '';
  // All fields are now optional
  emit('save', { ...address.value });
}
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal-wrapper {
  width: 400px;
}
.modal-container {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
}
</style>
