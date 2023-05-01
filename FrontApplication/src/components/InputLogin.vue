<template>
  <div :style="'margin: 0 auto; width: '+tamanho+'%; padding-top: 10px;'">
    <b-form-group
        :label="label"
        label-for="textarea-formatter"
        :description="textoDescricao"
        class="mb-0"
    >
      <b-form-input
          id="textarea-formatter"
          v-model="user.login"
          placeholder="Enter your text"
      ></b-form-input>
    </b-form-group>
  </div>
</template>

<script>
export default {
  name: "InputLogin",
  props: {
    tamanho: {
      default: 30,
      type: Number
    },
    user: undefined,
    textoDescricao: {
      default: "",
      type: String
    },
    label: {
      default: "",
      type: String,
    }
  }
}
</script>

<style scoped>

</style>