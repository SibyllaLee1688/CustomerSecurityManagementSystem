function getRootWin(){   
    var win = window;   
    while (win != win.parent){   
        win = win.parent;   
    }   
    return win;   
}

/*
 *异步http请求的session超时
*a）所有的ajax请求均带有x-requested-with:XMLHttpRequest头信息
*b）Ext.Ajax是单实例对象（非常重要，全局单一Ext.Ajax实例！）
*c）注册Ext.Ajax的requestcomplete事件，每个ajax请求成功后首先响应该事件（概念类似spring的aop拦截）。 

Ext.Ajax.on(
		'requestcomplete',
		function (conn,response,options){   
		    //Ext重新封装了response对象   
		    if(typeof response.getResponseHeader.sessionstatus != 'undefined'){   
		        //发现请求超时，退出处理代码...   
				getRootWin().location.href="/CustomerSecurityManagementSystem/login/login.jsp";
		    }
		},
		this
);
*/

// Create ajax request with hinting message while process data
// also contains session time out management
var myAjax = new Ext.data.Connection({
	listeners : {        
		beforerequest : function() {
			   Ext.MessageBox.show({
			   title: 'Please wait',
			   progressText: 'Processing Data...',
			   width:300,
			   progress:true,
			   closable:false,
			   animEl: 'body'
			   });
			},
		requestcomplete : function(conn, response, options) {
				Ext.MessageBox.hide();
				if(typeof response.getResponseHeader.sessionstatus != 'undefined'){
					getRootWin().location.href="/CustomerSecurityManagementSystem/selfService/start.action";
				}
			}  
	}
});

   

Ext.onReady(function(){
	 // create the Data Store
    var store = new Ext.data.JsonStore({
        root: 'customerInfos',
        totalProperty: 'totalCount',
        idProperty: 'id',
        remoteSort: true,

        fields: [ {name: 'id', type: 'int'}, 'customerId', 'customerName', 'nationality', 'certificateType', 'certificateId', {name: 'certificateBeginDate', mapping: 'certificateBeginDate', type: 'date', dateFormat: 'Y-m-d\\TH:i:s'},	{name: 'certificateEndDate', mapping: 'certificateEndDate', type: 'date', dateFormat: 'Y-m-d\\TH:i:s'}, 'mobileNumber', 'phoneNumber', {name: 'foreignFlag', type: 'bool'}, 'sex', 'birthday', 'zipcode', 'certificateAddress', 'address', 'relationcertificateId', 'relationcertificateType', 'instrperName', 'customerType', 'professionCode', 'branch', {name: 'riskValue', type: 'int'}, 'riskType', 'remark' ],

        // ScriptTagProxy: load using script tags for cross domain, if the data in on the same domain as
        // this page, an HttpProxy would be better
        proxy: new Ext.data.HttpProxy ({
          url: document.getElementById('example')
        })
    });
    store.setDefaultSort('riskValue', 'desc');
	
    var pagingBar = new Ext.PagingToolbar({
        pageSize: 12,
        store: store,
        displayInfo: true,
        displayMsg: 'Displaying topics {0} - {1} of {2}',
        emptyMsg: "No topics to display"
    });

    // pluggable renders
	function renderCertificate(value, p, record){
		var str = '';
		if(value != null && value != '')
			str = str + String.format('<b>Type:</b>{0}<br/>', value);
		if(record.data.certificateId != null && record.data.certificateId != '')
			str = str + String.format('<b>ID:</b>{0}<br/>', record.data.certificateId);
		if(record.data.certificateBeginDate != null && record.data.certificateBeginDate != '')
			str = str + String.format('<b>BeginDate:</b>{0}<br/>', Ext.util.Format.date(record.data.certificateBeginDate,"Y-m-d"));
		if(record.data.certificateEndDate != null && record.data.certificateEndDate != '')
			str = str + String.format('<b>EndDate:</b>{0}<br/>', Ext.util.Format.date(record.data.certificateEndDate,"Y-m-d"));
		if(record.data.certificateAddress != null && record.data.certificateAddress != '')
			str = str + String.format('<b>Address:</b>{0}', record.data.certificateAddress);	
		return str;
    }
	
	function renderRelationCertificate(value, p, record){
        if(record.data.relationcertificateId == null && value == null)
			return String.format('');
		if(record.data.relationcertificateId == null)
			return String.format('<b>Type:</b>{0}', value);
		if(value == null)
			return String.format('<b>ID:</b>{0}', record.data.relationcertificateId);	
		return String.format(
                '<b>Type:</b>{0}<br/><b>ID:</b>{1}',
                value, record.data.relationcertificateId);
    }
	
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	
    var grid = new Ext.grid.GridPanel({
        id: 'customerInfosGrid',
		el:'customers',
        width:1000,
        height:300,
        title:'CustomerInfos List',
		autoScroll:true,
        store: store,
        trackMouseOver:false,
        disableSelection:true,
        loadMask: true,

        // grid columns
        columns:[
			sm,
		{
            id: 'info', // id assigned so we can apply custom css (e.g. .x-grid-col-info b { color:#333 })
            header: "CustomerID",
            dataIndex: 'customerId'
          //  width: 70,
          //  renderer: renderTopic,
          //  sortable: true
        },{
            header: "CustomerName",
            dataIndex: 'customerName',
          //  width: 100,
		  	hidden: true
          //  sortable: true
        },{
            header: "Nationality",
            dataIndex: 'nationality',
          //  width: 70,
		    hidden: true,
            align: 'left'
            //sortable: true
        },{
            header: "Certificate",
            dataIndex: 'certificateType',
		    renderer: renderCertificate,
            align: 'left'
          //  sortable: true
        },{
            header: "MobileNumber",
            dataIndex: 'mobileNumber',
			hidden: true
          //  width: 150,
          //  renderer: renderLast,
          //  sortable: true
        },{
            header: "PhoneNumber",
            dataIndex: 'phoneNumber',
			hidden: true
          //  width: 150,
          //  renderer: renderLast,
          //  sortable: true
        },{
            header: "ForeignFlag",
            dataIndex: 'foreignFlag',
          //  width: 150,
            renderer: function(value){if(value==true) {return "境外";} else {return "境内";}},
            sortable: true
        },{
            header: "Sex",
            dataIndex: 'sex',
			hidden: true
          //  width: 150,
          //  renderer: renderLast,
          //  sortable: true
        },{
            header: "Birthday",
            dataIndex: 'birthday',
			hidden: true
          //  width: 150,
          //  renderer: Ext.util.Format.dateRenderer('Y-m-d')
          //  sortable: true
        },{
            header: "ZipCode",
            dataIndex: 'zipcode',
			hidden: true
          //  width: 150,
          //  renderer: renderLast,
          //  sortable: true
        },{
            header: "Relationcertificate",
            dataIndex: 'relationcertificateType',
          //  width: 150,
            renderer: renderRelationCertificate,
			hidden: true
          //  sortable: true
        },{
            header: "InstrperName",
            dataIndex: 'instrperName',
          //  width: 150,
            sortable: true
        },{
            header: "CustomerType",
            dataIndex: 'customerType',
          //  width: 150,
          //  renderer: renderLast,
            sortable: true
        },{
            header: "ProfessionCode",
            dataIndex: 'professionCode'
          //  width: 150,
          //  sortable: true
        },{
            header: "Branch",
            dataIndex: 'branch',
          //  width: 150,
            sortable: true
        },{
            header: "RiskValue",
            dataIndex: 'riskValue',
          //  width: 150,
          //  renderer: renderLast,
            sortable: true
        },{
            header: "RiskType",
            dataIndex: 'riskType',
          //  width: 150,
            sortable: true
        },{
            header: "Remark",
            dataIndex: 'remark',
			hidden: true,
			align: 'right'
        }],

        // customize view config
        viewConfig: {
            forceFit:true,
            enableRowBody:true
            //showPreview:true,
			// Return CSS class to apply to rows depending upon preview
            //getRowClass : function(record, rowIndex, p, store){
              //  if(this.showPreview){
                //    p.body = '<p>'+record.data.excerpt+'</p>';
                  //  return 'x-grid3-row-expanded';
                //}
               // return 'x-grid3-row-collapsed';
           // }
        },
		sm: sm,
        // paging bar on the bottom
        bbar: pagingBar
    });
	grid.on("rowcontextmenu",function(grid,rowIndex,e){
            e.preventDefault();
			if ( rowIndex < 0 ) {
				return;
			}
            var treeMenu = new Ext.menu.Menu([
                {xtype:"button",text:"添加",icon:"../images/add.png",pressed:true,handler:function(){ Ext.Msg.alert("提示消息","该功能还未实现!");}},
                {xtype:"button",text:"编辑",icon:"../images/edit.png",pressed:true,handler:function(){
							var rows = Ext.getCmp("customerInfosGrid").getSelections();
							var record;
							if(rows.length >= 1) {
	                            record = rows[0];
			                } else {
								Ext.Msg.alert("提示消息","请选择一行数据然后才能够编辑!");
								return false;
							}
							//Create edit form
							var fs = new Ext.FormPanel({
									frame: true,
									//headerAsText: true,
									//bodyStyle: 'padding:5px 5px 5px 5px',
									//style: 'padding:5px 5px 5px 5px;align:left',
									anchor: '99%',
									//defaultType: "textfield",
									defaults: { anchor: this["anchor"] == null ? '95%' : this["anchor"] },
									//baseCls: "x-plain",
									labelAlign: 'right',
									bodyStyle:'padding:5px 5px 0',
									labelWidth: 150,
									width:700,
									waitMsgTarget: true,
									method: 'POST',
									//disabled: true,
									items: [
										new Ext.form.FieldSet({
											autoHeight: true,
											items: [{
													layout:'column',
													items:[{
															columnWidth:.5,
															layout: 'form',
															items: [{
																xtype:'textfield',
																fieldLabel: 'CustomerID',
																name: 'customerInfo.customerId',
																value: record.data.customerId
																//anchor:'95%'
															}, {
																xtype:'textfield',
																fieldLabel: 'CustomerType',
																name: 'customerInfo.customerType',
																value: record.data.customerType
															}]
													},{
															columnWidth:.5,
															layout: 'form',
															items: [{
																xtype:'textfield',
																fieldLabel: 'RiskValue',
																name: 'customerInfo.riskValue',
																value: record.data.riskValue
															},{
																xtype:'textfield',
																fieldLabel: 'RiskType',
																name: 'customerInfo.riskType',
																value: record.data.riskType
															}]
													}]
											},{
												xtype:'textarea',
												fieldLabel: 'Remark',
												name: 'customerInfo.remark',
												value: record.data.remark,
												height: 60,
												width: 300
											},{
												xtype:'tabpanel',
												plain:true,
												activeTab: 0,
												height:180,
												defaults:{bodyStyle:'padding:10px'},
												items:[{
													title:'Branch Details',
													layout:'form',
													defaults: {width: 230},
													defaultType: 'textfield',
													items: [{
															fieldLabel: 'Branch',
															hiddenName: 'customerInfo.branch',
															value: record.data.branch,
															// 需要更新营业部信息
															store: new Ext.data.SimpleStore({data: [["总部"],["北京"]], fields:["branch"]}),
															xtype: 'combo',
															mode: 'local',
															triggerAction: 'all',
															forceSelection: true,
															displayField: 'branch',
															valueField: 'branch',
															typeAhead:true,
															//allowBlank: false,
															editable: false,
															anchor:'50%'
													},{
															xtype:'textfield',
															fieldLabel: 'InstrperName',
															name: 'customerInfo.instrperName',
															value: record.data.instrperName
													},{
															xtype:'textfield',
															fieldLabel: 'RelationcertificateId',
															name: 'customerInfo.relationcertificateId',
															value: record.data.relationcertificateId
													}, {
															xtype:'textfield',
															fieldLabel: 'RelationcertificateType',
															name: 'customerInfo.relationcertificateType',
															value: record.data.relationcertificateType
													}]
												},{
													title:'Certificate Details',
													layout:'form',
													defaults: {width: 230},
													defaultType: 'textfield',
													items: [{
															fieldLabel: 'certificateID',
															name: 'customerInfo.certificateId',
															value: record.data.certificateId
													}, {
															fieldLabel: 'CertificateType',
															name: 'customerInfo.certificateType',
															value: record.data.certificateType
															//vtype: 
															// allowBlank:false,
													},{
															xtype: 'datefield',
															fieldLabel: 'CertificateBeginDate',
															name: 'customerInfo.certificateBeginDate',
															value: record.data.certificateBeginDate,
															format: "Y-m-d"
													}, {
															fieldLabel: 'CertificateEndDate',
															name: 'customerInfo.certificateEndDate',
															value: record.data.certificateEndDate,
															xtype: 'datefield',
															format: "Y-m-d"
													},{
															//xtype:'textfield',
															fieldLabel: 'CertificateAddress',
															name: 'customerInfo.certificateAddress',
															value: record.data.certificateAddress
													}]
												},{
													title:'Contact Details',
													layout:'form',
													defaults: {width: 230},
													defaultType: 'textfield',
													items: [{
															xtype:'textfield',
															fieldLabel: 'MobileNumber',
															name: 'customerInfo.mobileNumber',
															value: record.data.mobileNumber
													},{
															xtype:'textfield',
															fieldLabel: 'PhoneNumber',
															name: 'customerInfo.phoneNumber',
															value: record.data.phoneNumber
													},{
															xtype:'textfield',
															fieldLabel: 'Zipcode',
															name: 'customerInfo.zipcode',
															value: record.data.zipcode
													},{
															xtype:'textfield',
															fieldLabel: 'Address',
															name: 'customerInfo.address',
															value: record.data.address
													},{
															xtype:'textfield',
															fieldLabel: 'Nationality',
															name: 'customerInfo.nationality',
															value: record.data.nationality
													}]
												},{
													title:'Person Details',
													layout:'form',
													defaults: {width: 230},
													defaultType: 'textfield',
													items: [{
															xtype:'textfield',
															fieldLabel: 'CustomerName',
															name: 'customerInfo.customerName',
															value: record.data.customerName
													},{
															fieldLabel: 'Sex',
															hiddenName: 'customerInfo.sex',
															value: record.data.sex,
															store: new Ext.data.SimpleStore({data: [["男"],["女"]], fields:["sex"]}),
															xtype: 'combo',
															mode: 'local',
															triggerAction: 'all',
															forceSelection: true,
															displayField: 'sex',
															valueField: 'sex',
															typeAhead:true,
															//allowBlank: false,
															editable: false,
															anchor:'50%'
													}, {
															fieldLabel: 'Birthday',
															name: 'customerInfo.birthday',
															value: record.data.birthday,
															xtype: 'datefield',
															format: 'Y-m-d'
													}, {
															xtype:'textfield',
															fieldLabel: 'ProfessionCode',
															name: 'customerInfo.professionCode',
															value: record.data.professionCode
													},{
															fieldLabel: 'ForeignFlag',
															hiddenName: 'customerInfo.foreignFlag',
															value: record.data.foreignFlag,
															store: new Ext.data.SimpleStore({data: [[true, "境外"],[false, "境内"]], fields:["foreignFlag","foreignFlagDesc"]}),
															xtype: 'combo',
															mode: 'local',
															triggerAction: 'all',
															forceSelection: true,
															displayField: 'foreignFlagDesc',
															valueField: 'foreignFlag',
															typeAhead:true,
															//allowBlank: false,
															editable: false,
															anchor:'50%'
													}]
												}]
											},{
												fieldLabel: 'id',
												xtype: 'hidden',
												name: 'customerInfo.id',
												type: 'int',
												value: record.data.id
												}]
											})
										   ]
							});
													
							// EXT Window contains form
							var win = new Ext.Window({
											title: 'Edit Customer Information',
											modal:true,
											items: [fs],
											plain: true,
											width: 700,
											buttons:[{
												text: " OK ",
												closeAction: function(){ win.destroy();},
												handler: function(){
													myAjax.request({
														url:'/CustomerSecurityManagementSystem/example/save.action',
														//method:POST,
														params: fs.getForm().getValues(false),
														success: function(result, request) {
															var jsonData = Ext.util.JSON.decode(result.responseText); 
															Ext.Msg.alert("Success", jsonData.msg);
															Ext.getCmp("customerInfosGrid").store.reload();
															win.destroy();				
														},
														failure: function(form, action) {
															switch (action.failureType) {
																case Ext.form.Action.CLIENT_INVALID:
																	Ext.Msg.alert("Failure", "Form fields may not be submitted with invalid values");
																	break;
																case Ext.form.Action.CONNECT_FAILURE:
																	Ext.Msg.alert("Failure", "Ajax communication failed");
																	break;
																case Ext.form.Action.SERVER_INVALID:
																   var jsonData = Ext.util.JSON.decode(result.responseText);
																   Ext.Msg.alert("Failure", jsonData.msg);
														   }
														}
													})														
												}														
											}, {
												text: "Cancel",
												handler: function(){
													win.destroy();
													//fs.destroy();
												}
											}]
										});
							win.show();
							//fs.show();
						}
				},
				//{xtype:"button",text:"隐藏",icon:"../images/arrow-down.gif",pressed:true,handler:function(){ Ext.Msg.alert("提示消息","该功能还未实现!");}},
               // {xtype:"button",text:"显示",icon:"../images/arrow-up.gif",pressed:true,handler:function(){ Ext.Msg.alert("提示消息","该功能还未实现!");}},
                {xtype:"button",text:"删除",icon:"../images/delete.png",pressed:true, handler:function(){
					var rows = Ext.getCmp("customerInfosGrid").getSelections();
					var record;
					if(rows.length >= 1) {
	                            record = rows[0];
	                } else {
						Ext.Msg.alert("提示消息","请选择一行数据然后才能够删除!");
						return false;
					}					

					myAjax.request({
						url:'/CustomerSecurityManagementSystem/example/delete.action',
						//method:post,
						params:{id:record.data.id},
						success: function(result, request) {
							   var jsonData = Ext.util.JSON.decode(result.responseText);                
							   Ext.Msg.alert("Success", jsonData.msg);
							   Ext.getCmp("customerInfosGrid").store.reload();				
							},
						failure: function(form, action) {
								switch (action.failureType) {
									case Ext.form.Action.CLIENT_INVALID:
										Ext.Msg.alert("Failure", "Form fields may not be submitted with invalid values");
										break;
									case Ext.form.Action.CONNECT_FAILURE:
										Ext.Msg.alert("Failure", "Ajax communication failed");
										break;
									case Ext.form.Action.SERVER_INVALID:
									   var jsonData = Ext.util.JSON.decode(result.responseText);
									   Ext.Msg.alert("Failure", jsonData.msg);
							   }
							}
						})
				}}
            ]);
            treeMenu.showAt(e.getPoint());
        }); 

    // render it
    grid.render();

	// Create file upload form
	
	var fileUpload = new Ext.FormPanel({
        labelAlign: 'right',
        title: 'Upload Customer Source File',
        bodyStyle:'padding:5px',
		//frame: true,
        width: 600,
		renderTo: 'customersSource',
		fileUpload: true,
		//baseCls: 'x-plain', 
		items: [{
			xtype:'textfield', 
			inputType: 'file',
			allowBlank:false, 
			fieldLabel: '文件',  
			disabled:false, 
			name: 'upload', 
			//maxLength:25,
			blankText: '请上传文件',			
			anchor:'90%'
		}],
		buttons: [{
			text: '上传',
			handler: function(){
				fileUpload.getForm().submit({
					//clientValidation: true,
					//method: POST,
				    url: '/CustomerSecurityManagementSystem/example/upload.action',				 
				    success: function(form, action) {
				       Ext.Msg.alert("Success", action.result.msg);
				    },
				    failure: function(form, action) {
				        switch (action.failureType) {
				            case Ext.form.Action.CLIENT_INVALID:
				                Ext.Msg.alert("Failure", "Form fields may not be submitted with invalid values");
				                break;
				            case Ext.form.Action.CONNECT_FAILURE:
				                Ext.Msg.alert("Failure", "Ajax communication failed");
				                break;
				            case Ext.form.Action.SERVER_INVALID:
				               Ext.Msg.alert("Failure", action.result.msg);
				       }
				    }

				})
			}
        }]
	});
	
    // trigger the data store load
    store.load({params:{start:0, limit:12}});
});
