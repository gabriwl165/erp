<template>
  <div :style="'display: '+centralizar+'; padding-left: 10px; width: '+tamanho+'%;margin: 0 auto'">
    <b-form-group
        :label="label"
        label-for="textarea-formatter"
        :description="textoDescricao"
        class="mb-0"
    >

      <b-form-select @input="select" v-model="selected" :options="options" class="mb-3"
                     style=" margin-bottom: 0px !important;"></b-form-select>
      <div v-if="debug" class="mt-3">Selected: <strong>{{ selected }}</strong></div>
    </b-form-group>
  </div>
</template>

<script>
export default {
  prop: ['value'],
  props: {
    debug: {
      default: false,
      type: Boolean
    },
    selected: null,
    options: [],
    label: {
      default: "",
      type: String
    },
    textoDescricao: {
      default: "",
      type: String
    },
    centralizar: {
      default: "inline-block",
      type: String
    },
    tamanho: {
      default: 30,
      type: Number
    }
  },
  data() {
    return {

      // options: [
      //   { value: 'a', text: 'This is First option' },
      // ]
    }
  },
  methods: {
    select(e) {
      this.$emit('select', e)
    }
  }
}
</script>

<!--

13/08/2022 -> Problema com o combo box, CSS não estava funcionando, precisei somente atualizar uma dependência
do projeto com npm:

https://stackoverflow.com/questions/68846511/b-form-select-vue-bootstrap-style-css-issue

-->