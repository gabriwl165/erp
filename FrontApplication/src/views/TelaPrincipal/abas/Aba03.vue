<template>
    <div>
        <ImageUpload :image="image" @save="saveFile"/>
        <b-modal id="cat-erro" size="lg">
            <img :src="urlCatErro"/>
        </b-modal>
        <Button label="Loading Keys" :click="loadKeys"/>
        <ListTable :colunas="colunas" :items="itens" tamanho="30" :mostrar-botoes="false" :selecionarMultiplos="false"
                   :textoBotaoInline="'Mostrar Imagem'" @botaoCustomClick="botaoClick"/>
        <b-modal id="imageModal" size="lg">
            <img id="image" :src="imageSource"/>
        </b-modal>
    </div>
</template>

<script>
import ImageUpload from "@/components/ImageUpload";
import axios from "axios";
import HTTPRequestMixin from "@/mixins/HTTPRequestMixin";
import Button from "@/components/Button";
import ListTable from "@/components/ListTable";
import ToastMixin from "@/mixins/ToastMixin"

export default {
    name: "Aba03",
    components: {ListTable, ImageUpload, Button},
    mixins: [HTTPRequestMixin, ToastMixin],
    props: {
      url: String
    },
    data: () => ({
        image: null,
        urlCatErro: '',
        colunas: [
            {key: 'nome', label: 'nome'},
            {key: 'actions', label: 'Ações'}
        ],
        itens: [],
        imageSource: ''
    }),
    methods: {
        botaoClick(index) {
            let key = this.itens[index]['nome']
            console.log(key);

            this.postRequest(`/image/get`, key, (resp) => {
                this.$bvModal.show("imageModal")
                debugger
                const blob = resp.object;
                console.log(resp)

                let imageBlob = (new Blob([blob], {type: 'image/png'}));

                var urlCreator = window.URL || window.webkitURL

                let image = urlCreator.createObjectURL(imageBlob)
                this.imageSource = "data:image/gif;base64," + blob;


            }, resp => {
                console.log(resp)
                this.urlCatErro = `https://http.cat/${resp.status}.jpg`
                this.$bvModal.show("cat-erro")
            })
        },
        loadKeys() {
            this.getRequest(`/image/getKeys`, resp => {
                this.itens = resp.object
            }, resp => {
                console.log(resp)
                this.urlCatErro = `https://http.cat/${resp.status}.jpg`
                this.$bvModal.show("cat-erro")
            })
        },
        saveFile(imagem) {
            const formData = new FormData();
            formData.append("image", imagem)
            formData.append("name", imagem.name)
            axios.post(`/image/save`, formData).then(async (resp) => {
                debugger
                if(resp.object.success) {
                    this.image = null
                    debugger
                    this.makeToast("Salvo com sucesso", "Sucesso")
                }
            }).catch(resp => {
                console.log(resp)
                this.urlCatErro = `https://http.cat/${resp.status}.jpg`
                this.$bvModal.show("cat-erro")
            })
        },
    },
    created() {
        this.loadKeys()
    }
}
</script>

<style scoped>

</style>