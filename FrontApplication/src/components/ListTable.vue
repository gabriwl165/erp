<template>
  <div :style="'margin: 0 auto; width: '+tamanho+'%; padding-top: 10px;'">
    <b-table
        :items="items"
        :fields="colunas"
        :select-mode="selectMode"
        responsive="sm"
        ref="selectableTable"
        :selectable="selecionarMultiplos"
        @row-selected="onRowSelected" style="border: 2px solid red; border-radius: 10px"
    >
      <!-- Example scoped slot for select state illustrative purposes -->
      <template #cell(selected)="{ rowSelected }">
        <template v-if="rowSelected">
          <span aria-hidden="true">&check;</span>
          <span class="sr-only">Selecionado</span>
        </template>
        <template v-else>
          <span aria-hidden="true">&nbsp;</span>
          <span class="sr-only">Não selecionado</span>
        </template>
      </template>
      <template #cell(actions)="row">
        <b-button size="sm" class="mr-1" @click="botaoCustomClick(row.index)">
          {{ textoBotaoInline }}
        </b-button>
      </template>
    </b-table>
    <p v-if="mostrarBotoes">
      <b-button v-if="selectMode === 'multi' " size="sm" @click="selectAllRows" style="margin-right: 5px !important;">Selecionar todos</b-button>
      <b-button v-if="selectMode === 'multi' " size="sm" @click="clearSelected" style="margin-right: 5px !important;">Desmarcar todos</b-button>
      <b-button size="sm" @click="editarRegistro" style="margin-right: 5px !important;">Editar</b-button>
      <b-button size="sm" @click="editarRegistro" style="margin-right: 5px !important;">Excluir</b-button>
    </p>
    <p v-if="debugList">
      Selected Rows:<br>
      {{ selected }}
    </p>
  </div>
</template>

<script>
export default {
  props:{
    textoBotaoInline: {
      type: String,
    },
    items: [],
    colunas: [],
    selectMode: String,
    selecionarMultiplos: {
      type: Boolean,
      default: true
    },
    mostrarBotoes: {
      default: true,
      type: Boolean
    },
    debugList: {
      default: false,
      type: Boolean
    },
    tamanho: {
      default: 100,
      type: [String, Number]
    }

  },
  data() {
    return {
      modes: ['multi', 'single', 'range'],
      selected: [],
    }
  },
  methods: {
    botaoCustomClick(index){
      this.$emit("botaoCustomClick", index)
    },
    editarRegistro(){
      this.$emit("ObjetoEditar", this.selected)
    },
    excluirRegistro(){
      this.$emit("ObjetoExcluir", this.selected)
    },
    onRowSelected(items) {
      this.selected = items
    },
    selectAllRows() {
      this.$refs.selectableTable.selectAllRows()
    },
    clearSelected() {
      this.$refs.selectableTable.clearSelected()
    },
    selectThirdRow() {
      // Rows are indexed from 0, so the third row is index 2
      this.$refs.selectableTable.selectRow(2)
    },
    unselectThirdRow() {
      // Rows are indexed from 0, so the third row is index 2
      this.$refs.selectableTable.unselectRow(2)
    }
  }
}
</script>