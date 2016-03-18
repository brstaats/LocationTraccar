Ext.define('Traccar.view.JobsController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.jobs',


    onShowClick: function () {
        var deviceId

        deviceId = this.lookupReference('deviceField').getValue();
        console.log("whee")

        store = Ext.getStore('Jobs');
        console.log(store);
        store.load({
                    params: {
                        deviceId: deviceId
                    }
                });
        updateTable(jobs);
    },

    onClearClick: function () {
        Ext.getStore('Positions').removeAll();
    }

});