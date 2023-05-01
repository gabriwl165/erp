<template>
  <div class="topnav">
    <div style="display: inline-block;">
      <b-button v-b-toggle.sidebar-1 style="text-align: right !important;">
        <b-icon icon="person-fill" aria-hidden="true"></b-icon>
      </b-button>
    </div>
    <div style="text-align: center !important; width: 90%; display: inline-block;">
      <span style="color: aliceblue;">{{textoCentral}}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "TopBar",
  props: {
    textoCentral: {
      type: String,
      default: "Bem - Vindo"
    }
  }
}
</script>

<style scoped>
.topnav {
  background-color: #333;
  overflow: hidden;
}
</style>