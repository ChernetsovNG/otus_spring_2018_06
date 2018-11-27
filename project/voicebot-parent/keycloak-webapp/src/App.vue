<template>
    <div id="app">
        <keycloak-login></keycloak-login>
        <billing></billing>
    </div>
</template>

<script>
    import KeycloakLogin from "./components/KeycloakLogin";
    import Billing from "./components/Billing";

    export default {
        name: 'app',
        components: {
            Billing,
            KeycloakLogin
        },
        data: function () {
            return {
                appUuid: 'test-billing-123456',
                campaignsList: '5be82e19e40c43addaf6dc22',
                billingData: ''
            }
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }
</style>
