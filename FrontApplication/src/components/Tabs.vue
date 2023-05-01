<template>
  <div>
    <b-card no-body>
      <b-tabs card>
        <slot/>
      </b-tabs>
    </b-card>
  </div>
</template>

<script>
export default {
  name: "Tabs"
}
</script>

<style scoped>

</style>