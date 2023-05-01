<template>
  <div>
    <Input centralizar="block" label="Login" :value="form.login" @input="e => form.login = e"/>
    <Input centralizar="block" type="password" label="Senha" :value="form.senha" @input="e => form.senha = e"/>
    <Input centralizar="block" label="E-mail" :value="form.email" @input="e => form.email = e"/>
    <Input :tamanho="30" centralizar="block" type="date" label="Data Nascimento" :value="form.dataNascimento"
           @input="e => form.dataNascimento = e" :user="form.dataNascimento"/>
    <ComboBox centralizar="block" :value="form.funcao" @select="e => form.funcao = e" :options="options"
              :selected="form.funcao" texto-descricao="Função do usuário" label="Função" :debug="false"/>
    <b-button style="margin: 0 auto; width: 20%; display: block" v-b-modal.modal-1 @click="salvar">Salvar</b-button>
    <b-modal @ok="adicionarEndereco" id="bv-modal-adicionarEndereco">
      <div class="d-block text-center">
        <h3>Gostaria de salvar endereço do novo usuário?</h3>
      </div>
    </b-modal>
    <b-modal @ok="salvarEndereco" :ok-title="'Salvar Endereço'" id="bv-modal-endereco">
      <div class="d-block text-center">
        <Input centralizar="block" @blur="buscarCEP" :user="cep" label="Digite seu CEP" :value="cep"
               @input="e => cep = e"/>
        <Input :tamanho="13" :readonly="true" :user="endereco.siglaUF" label="UF" :value="endereco.siglaUF"
               @input="e => endereco.siglaUF = e"/>
        <Input :tamanho="15" @blur="buscarCEP" :user="endereco.numero" label="Número" :value="endereco.numero"
               @input="e => endereco.numero = e"/>
        <br/>
        <Input :tamanho="80" :readonly="true" :user="endereco.endereco" label="Endereço" :value="endereco.endereco"
               @input="e => endereco.endereco = e"/>
        <Input :tamanho="80" :readonly="true" :user="endereco.bairro" label="Bairro" :value="endereco.bairro"
               @input="e => endereco.bairro = e"/>

      </div>
    </b-modal>
  </div>
</template>

<script>
import Input from "@/components/Input";
import ComboBox from "@/components/ComboBox";
import axios from "axios";
import HTTPRequestMixin from "../../../mixins/HTTPRequestMixin"

export default {
  name: "Aba01",
  components: {Input, ComboBox},
  mixins: [HTTPRequestMixin],
  data: () => {
    return {
      form: {
        login: undefined,
        senha: undefined,
        dataNascimento: undefined,
        funcao: undefined,
        email: undefined
      },
      endereco: {
        siglaUF: undefined,
        endereco: undefined,
        numero: undefined,
        bairro: undefined,
      },
      options: [
        {value: null, text: 'Selecione uma opção'},
        {value: 'RECEPCAO', text: 'RECEPCAO'},
        {value: 'GESTOR', text: 'GESTOR'},
        {value: 'MODERADOR', text: 'MODERADOR'},
      ],
      cep: undefined,
      idNovoUsuario: undefined
    }
  },
  methods: {
    adicionarEndereco() {
      this.$bvModal.show('bv-modal-endereco')
    },
    salvar() {

      let url = `http://${this.url}:8080/usuario/salvar`;
      let obj = this.form;
      this.postRequest(url, obj, resp => {
        this.$bvModal.show('bv-modal-adicionarEndereco')
        this.idNovoUsuario = resp.object
      }, resp => console.log(resp))
    },
    buscarCEP() {
      axios.get('https://viacep.com.br/ws/' + this.cep + '/json/').then(resp => {
        console.log(resp)
        this.endereco.siglaUF = resp.data.uf;
        this.endereco.endereco = resp.data.logradouro
        this.endereco.bairro = resp.data.bairro
      }).catch(resp => {
        console.log(resp)
      })
    },
    async salvarEndereco() {
      let url = `http://${this.url}:8080/usuarioEndereco/salvar?idUsuario=${this.idNovoUsuario}`;
      let obj = this.endereco;
      await this.postRequest(url, obj, resp => {
            this.idNovoUsuario = resp.data.object
          },
          resp => console.log(resp)
      )
    }
  }
}
</script>

<style scoped>

</style>