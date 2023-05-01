<template>
  <div>

    <TopBar/>
    <Tabs>
      <Tab label="Cadastro de usuário">
        <Aba01/>
      </Tab>
      <Tab label="Usuários cadastrados">
        <ListTable tamanho="50" :items="items" :colunas="colunas" :selectMode="'single'" :mostrar-botoes="true"
                   @ObjetoEditar="editarRegistro" @ObjetoExcluir="excluirRegistro" :debugList="false"/>
      </Tab>
      <Tab label="Tab 3">
        <Aba03/>
      </Tab>
    </Tabs>


    <b-sidebar id="sidebar-1" title="Usuário" shadow>
      <b-button v-b-toggle.sidebar-1 style="margin: 0 auto; width: 30%; display: block">
        Ver perfil
      </b-button>
    </b-sidebar>

    <b-modal id="bv-modal-example" hide-footer size="xl">
      <EditUser :user="usuarioSelecionado"/>
      <b-button class="mt-3" block @click="$bvModal.hide('bv-modal-example')">Fechar</b-button>
    </b-modal>
    <img id="ItemPreview" src=""/>
  </div>
</template>

<script>
import ListTable from "@/components/ListTable";
import TopBar from "@/components/TopBar";
import Tabs from "@/components/Tabs";
import Tab from "@/components/Tab";

import axios from 'axios';
import EditUser from "@/views/EditUser";
import Aba01 from "@/views/TelaPrincipal/abas/Aba01";
import Button from "@/components/Button";
import Aba03 from "@/views/TelaPrincipal/abas/Aba03";

export default {
  name: "TelaPrincipal",
  components: {Aba03, Aba01, EditUser, Tab, TopBar, ListTable, Tabs,},
  data() {
    return {
      image: null,
      usuarioSelecionado: undefined,
      teste: {
        password: undefined
      },
      colunas: ['login', 'dataNascimento', 'funcao'],
      items: [],
      selectMode: 'multi',
      selected: [],
    }
  },
  methods: {
    makeToast(message, title, time = 5000, append = false) {
      this.$bvToast.toast(`${message}`, {
        title: title,
        autoHideDelay: time,
        appendToast: append
      })
    },
    editarRegistro(obj) {
      if (obj.length !== 0) {
        this.$bvModal.show('bv-modal-example')
        this.usuarioSelecionado = obj[0]
        console.log(obj)
      } else {
        this.makeToast("Favor selecionar um usuário", "Nenhum usuário selecionado")
      }
    },
  },
  created() {
    axios.get('http://localhost:8080/usuario/', {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem("access_token")}`
      }
    }).then(resp => {
      console.log(resp.data.object)
      this.items = resp.data.object

    }).catch(resp => console.log(resp))
  },
}

</script>

<style scoped>

</style>