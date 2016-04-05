Ext.define('Traccar.store.Jobs', {
    extend: 'Ext.data.Store',
    model: 'Traccar.model.Jobs',

    proxy: {
        type: 'rest',
        url: '/api/jobs'
    }
});