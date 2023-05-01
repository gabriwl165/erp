<template>
  <div style="padding-top: 10px; display: inline-block">
  <b-button @click="click" v-b-modal.modal-1 style="margin: 0 auto; display: inline-block;">{{label}}</b-button>
  </div>
</template>

<script>
export default {
  name: "Button",
  props: {
    label: {
      type: String,
      default: ""
    },
    click:{
      type: Function
    }
  }
}
</script>

<style scoped>

</style>