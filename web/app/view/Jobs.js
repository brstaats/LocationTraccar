Ext.define('Traccar.view.Jobs', {
    extend: 'Ext.grid.Panel',
    xtype: 'reportJobs',

    requires: [
        'Traccar.view.JobsController'//,
        //'Traccar.view.ReportController'
    ],

    controller: 'jobs',
    store: 'Jobs',

    title: Strings.reportTitle2,

    tbar: [{
        xtype: 'tbtext',
        html: Strings.reportTitle2
    },' ',' ',' ',{
        xtype: 'tbtext',
        html: Strings.reportDevice
    }, {
        xtype: 'combobox',
        reference: 'deviceField',
        store: 'Devices',
        valueField: 'id',
        displayField: 'name',
        typeAhead: true,
        queryMode: 'local'
    }, '-', {
        text: Strings.reportShow,
        handler: 'onShowClick'
    }, {
        text: Strings.reportClear,
        handler: 'onClearClick'
    }],

    columns: [{
        text: Strings.jobName,
        dataIndex: 'jname',
        flex: 1,
        renderer: Traccar.AttributeFormatter.getFormatter('valid') //nog aanpassen!!
    }, {
        text: Strings.positionAddress,
        dataIndex: 'address',
        flex: 1,
        renderer: Traccar.AttributeFormatter.getFormatter('address')
    }, {
        text: Strings.jobStartdate,
        dataIndex: 'jstartdate',
        flex: 1,
        xtype: 'datecolumn',
        renderer: Traccar.AttributeFormatter.getFormatter('fixTime')
    }, {
        text: Strings.jobEndDate,
        dataIndex: 'jendDate',
        flex: 1,
        xtype: 'datecolumn',
        renderer: Traccar.AttributeFormatter.getFormatter('fixTime')
    },{
        text: Strings.positionLatitude,
        dataIndex: 'latitude',
        flex: 1,
        renderer: Traccar.AttributeFormatter.getFormatter('latitude')
    }, {
        text: Strings.positionLongitude,
        dataIndex: 'longitude',
        flex: 1,
        renderer: Traccar.AttributeFormatter.getFormatter('latitude')
    }, {
        text: Strings.positionAltitude,
        dataIndex: 'altitude',
        flex: 1,
        renderer: Traccar.AttributeFormatter.getFormatter('altitude')
    }, {
        text: Strings.jobOuterregion,
        dataIndex: 'outerregion',
        flex: 1,
        renderer: Traccar.AttributeFormatter.getFormatter('speed')
    }, {
        text: Strings.jobInnerregion,
        dataIndex: 'innerregion',
        flex: 1,
        renderer: Traccar.AttributeFormatter.getFormatter('speed')
    }, {
        text: Strings.jobPriority,
        dataIndex: 'jpriority',
        flex: 1,
        renderer: Traccar.AttributeFormatter.getFormatter('speed')
    }, {
        text: Strings.jobFinished,
        dataIndex: 'jfinished',
        flex: 1,
        renderer: Traccar.AttributeFormatter.getFormatter('speed')
    }]
});