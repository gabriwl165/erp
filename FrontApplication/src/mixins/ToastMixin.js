export default {
    methods: {
        makeToast(message, titulo, time = 5000, appendToast = false) {
            this.$bvToast.toast(`${message}`, {
                title: titulo,
                autoHideDelay: time,
                appendToast: appendToast
            })
        }
    }
}