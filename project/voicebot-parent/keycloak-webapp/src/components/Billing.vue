<template>
    <div id="billing">
        <form>
            ID Приложения: <input v-model="appUuid" placeholder="id приложения">
            <br>
            Список кампаний: <input v-model="campaignsList" placeholder="Список кампаний">
            <br>
            Start datetime: <datetime type="datetime" value-zone="UTC" v-model="datetimeStart"></datetime>
            Finish datetime: <datetime type="datetime" value-zone="UTC" v-model="datetimeFinish"></datetime>
            <button type="submit" v-on:click.prevent="getBillingData">Get billing data</button>
        </form>
        <br>
        <pre>{{ $data }}</pre>
    </div>
</template>

<script>
    import axios from "axios";
    import Datetime from 'vue-datetime';
    import Vue from 'vue';
    import 'vue-datetime/dist/vue-datetime.css';
    import {Settings} from 'luxon';

    Settings.defaultLocale = 'ru';

    Vue.use(Datetime);

    export default {
        name: "Billing",
        created() {
            console.log('Billing');
        },
        data() {
            return {
                appUuid: 'test-billing-123456',
                campaignsList: '5be82e19e40c43addaf6dc22',
                datetimeStart: null,
                datetimeFinish: null,
                billingData: null
            }
        },
        methods: {
            getBillingData() {
                let body = {
                    "campaignIds": this.campaignsList.split(',')
                };

                const headers = {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                };

                const params = {
                    'appUuid': this.appUuid
                };

                console.log(headers);

                axios.post('http://localhost:8101/billing/spent-resources',
                    body, {headers: headers, params: params})
                    .then(result => this.billingData = result.data)
            }
        }
    }
</script>

<style scoped>

</style>
