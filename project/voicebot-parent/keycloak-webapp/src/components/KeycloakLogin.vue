<template>
    <div id="keycloak-login">
        <button type="submit" class="btn" v-on:click.prevent="keycloakRegister">Login Keycloak</button>
    </div>
</template>

<script>
    import Keycloak from '../../node_modules/keycloak-js';

    const keycloak = new Keycloak({
        url: 'https://keycloak.ivoice.online/auth',
        realm: 'billing',
        clientId: 'billing-test-client',
        "enable-cors": true
    });

    export default {
        name: "Login",
        created() {
            console.log('Логин');
        },
        methods: {
            keycloakRegister() {
                keycloak.init({onLoad: 'login-required'})
                    .success(function (authenticated) {
                        console.log(`Login in Keycloak: ${authenticated}`);
                        localStorage.setItem('token', keycloak.token);
                    })
                    .error(function () {
                        console.log('Failed to login in Keycloak');
                    });
                setInterval(function () {
                    keycloak.updateToken(70)
                        .success(function (refreshed) {
                            if (refreshed) {
                                console.log('Token was successfully refreshed');
                                localStorage.setItem('token', keycloak.token);
                            } else {
                                console.log('Token is still valid');
                            }
                        })
                        .error(function () {
                            console.log('Failed to refresh the token, or the session has expired');
                        });
                }, 60000);
            }
        }
    }
</script>

<style scoped>

</style>
