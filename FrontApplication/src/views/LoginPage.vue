<template>
  <div style="padding-top: 10% !important;">
    <img src="../assets/user.png" alt="Responsive image"
         style="margin: 0 auto !important; width: 10%; display: block "/>
    <Input centralizar="block" label="Login" :value="userLogin.username" @input="e => userLogin.username = e"/>
    <Input centralizar="block" type="password" label="Senha" :value="userLogin.password"
           @input="e => userLogin.password = e"/>
    <b-button v-b-modal.modal-1 @click="teste" style="margin: 0 auto; width: 10%; display: block">Button</b-button>
    <b-modal id="bv-modal-example" hide-footer>
      <template #modal-title>
        Erro!
      </template>
      <div class="d-block text-center">
        <h3>Usuário não encontrado!</h3>
      </div>
      <b-button class="mt-3" block @click="$bvModal.hide('bv-modal-example')">Fechar</b-button>
    </b-modal>
    <b-modal id="cat-erro" size="lg">
      <img :src="urlCatErro"/>
    </b-modal>
  </div>
</template>

<script>
import axios from "axios";
import Input from "@/components/Input";
import router from "@/router";

export default {
  components: {Input},
  data: () => ({
    userLogin: {
      username: undefined,
      password: undefined
    },
    urlCatErro: ''
  }),
  methods: {
    async teste() {
      // formData.append("login", this.userLogin.username)
      // formData.append("senha", this.userLogin.password)
      let details = {
        'username': this.userLogin.username,
        'password': this.userLogin.password,
      }
      var formBody = []
      for (var property in details) {
        var encodedKey = encodeURIComponent(property);
        var encodedValue = encodeURIComponent(details[property]);
        formBody.push(encodedKey + "=" + encodedValue);
      }
      formBody = formBody.join("&");
      axios.post("http://localhost:8080/login", formBody, {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
        }
      }).then(resp => {
        console.log(resp)
        for (let att in resp.data) {
          localStorage.setItem(att, resp.data[att])
        }
        if (resp.status === 200) {
          router.push("/index")
        }
      }).catch((resp) => {
        this.urlCatErro =  `https://http.cat/${resp.status}.jpg`
        this.$bvModal.show("cat-erro")
      })
      // let res = await axios.post("http://localhost:8080/login", this.userLogin )
    }
  }
}
</script>

<style>
*.img {
  background-color: cornflowerblue;
}
</style>