<template>
  <div :style="'padding-bottom: 5px;padding-left: 10px;margin: 0 auto; width: '+tamanho+'%; display: '+centralizar">
    <b-form-group
        :label="label"
        label-for="textarea-formatter"
        :description="textoDescricao"
        class="mb-0"
    >
      <b-form-input
          id="type-password"
          :type="type"
          v-model="user"
          :placeholder="placeholder"
          @input="handleInput"
          @blur="emitarBlur"
          :readonly="readonly"
      ></b-form-input>
    </b-form-group>
  </div>
</template>

<script>
export default {
  name: "Input",
  prop: ['value'],
  emits: ["update:textValue"],
  props: {
    textValue: undefined,
    tamanho: {
      default: 30,
      type: Number
    },
    user: {},
    textoDescricao: {
      default: "",
      type: String
    },
    readonly:{
      default: false,
      type: Boolean
    },
    label: {
      default: "",
      type: String,
    },
    type: {
      default: "text",
      type: String
    },
    centralizar: {
      default: "inline-block",
      type: String
    },
    placeholder: {
      default: "",
      type: String
    }
  },
  data () {
    return {
      content: this.value
    }
  },
  methods: {
    handleInput(e){
      this.user = e
      console.log("This is the user: " + this.user)
      this.$emit('input', e)
    },
    emitarBlur(){
      this.$emit('blur')
    }
  }
}
</script>

<style scoped>

</style>