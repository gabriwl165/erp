<template>
  <router-view>
    <div>
      <router-link to="/">Login Page</router-link>
    </div>
  </router-view>
</template>

<script>

export default {
  components: {},
  data() {
    return {
      modes: ['multi', 'single', 'range'],
      colunas: ['Ativo', 'Idade', 'Nome', 'Sobrenome'],
      items: [
        {id: 1, Ativo: true, Idade: 40, Nome: 'Dickerson', Sobrenome: 'Macdonald'},
        {id: 2, Ativo: false, Idade: 21, Nome: 'Larsen', Sobrenome: 'Shaw'},
        {id: 3, Ativo: false, Idade: 89, Nome: 'Geneva', Sobrenome: 'Wilson'},
        {id: 4, Ativo: true, Idade: 38, Nome: 'Jami', Sobrenome: 'Carney'}
      ],
      selectMode: 'multi',
      selected: []
    }
  },
  methods: {
    editarRegistro(obj) {
      console.log(obj)
    },
    excluirRegistro(obj) {
      console.log(obj)
    }
  }
}
</script>