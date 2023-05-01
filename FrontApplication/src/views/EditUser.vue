<template>
  <div>
    <Input :tamanho="15" label="Login" texto-descricao="Login do usuário" :value="user.login"
           @input="e => user.login = e" :user="user.login"/>
    <Input :tamanho="15" type="date" label="Data Nascimento" texto-descricao="Data de nascimento"
           :value="user.dataNascimento" @input="e => user.dataNascimento = e" :user="user.dataNascimento"/>
    <ComboBox :value="user.funcao" @select="e => user.funcao = e" :options="options" :selected="user.funcao"
              texto-descricao="Função do usuário" label="Função" :debug="false"/>
    <div style="margin: 0 auto; width: 6%">
      <Button label="Salvar" :click="salvar"/>

    </div>
    <b-button style="margin: 0 auto; width: 14%; display: block; margin-top: 10px" v-b-modal.modal-1 @click="resetar">Resetar Senha</b-button>
  </div>
</template>

<script>
import Input from "@/components/Input";
import ComboBox from "@/components/ComboBox";
import Button from "@/components/Button";
import axios from "axios";

export default {
  name: "EditUser",
  components: {Button, ComboBox, Input},
  props: {
    user: Object
  },
  data: () => {
    return {
      options: [
        {value: null, text: 'Selecione uma opção'},
        {value: 'RECEPCAO', text: 'RECEPCAO'},
        {value: 'GESTOR', text: 'GESTOR'},
        {value: 'MODERADOR', text: 'MODERADOR'},
      ]
    }
  },
  methods: {
    salvar() {
      axios.post('http://localhost:8080/usuario/salvar', this.user).then(resp => {
        console.log(resp.data.object)
        this.items = resp.data.object
      }).catch(resp => console.log(resp))
    },
    resetar(){
      debugger
      const obj = {
        id: this.user.id
      }
      console.log(typeof this.user.id)
      axios.post('http://localhost:8080/usuario/resetarSenha', obj).then(resp => {
        console.log(resp.data.object)
        this.items = resp.data.object
      }).catch(resp => console.log(resp))
    }
  }
}
</script>

<style scoped>

</style>