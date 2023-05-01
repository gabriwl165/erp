<template>
  <b-tab :title="label">
    <slot/>
  </b-tab>
</template>

<script>
export default {
  name: "Tab",
  props: {
    label: String
  }
}
</script>

<style scoped>

</style>