<template>
  <div>
    <b-form-file
        v-model="image"
        :state="Boolean(image)"
        placeholder="Choose a file or drop it here..."
        drop-placeholder="Drop file here..."/>
    <Button label="Salvar" :click="saveFile"/>
  </div>
</template>

<script>
import Button from "@/components/Button";

export default {
  name: "ImageUpload",
  components: {Button},
  props: {
    image: null
  },
  data: () => ({}),
  methods: {
    saveFile() {
      this.$emit('save', this.image)
    }
  }
}
</script>

<style scoped>

</style>