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
import HTTPRequestMixin from "@/mixins/HTTPRequestMixin";

export default {
    name: 'LoginPage',
    components: {Input},
    mixins: [HTTPRequestMixin],
    props: {
        params: undefined
    },
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
            let formBody = []
            for (let property in details) {
                let encodedKey = encodeURIComponent(property);
                let encodedValue = encodeURIComponent(details[property]);
                formBody.push(encodedKey + "=" + encodedValue);
            }
            formBody = formBody.join("&");
            await this.postRequest(`/login`, formBody, resp => {
                console.log(resp)
                for (let att in resp.data) {
                    localStorage.setItem(att, resp.data[att])
                }
                router.push("/index")
            }, err => {
                this.urlCatErro = `https://http.cat/${err.status}.jpg`
                this.$bvModal.show("cat-erro")
            }, {
                "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
            })
        }
    },
    mounted() {
        console.log(this.params)
    }
}
</script>

<style>
*.img {
    background-color: cornflowerblue;
}
</style>