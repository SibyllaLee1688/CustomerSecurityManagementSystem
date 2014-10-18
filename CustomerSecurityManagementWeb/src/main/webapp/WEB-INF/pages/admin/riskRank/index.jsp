<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
 -->
<script src="<%=request.getContextPath() %>/webjars/dojo/1.10.0/dojo/dojo.js"></script>

		<script type="text/javascript" >
		
			require(["dojo/dom", "dojo/request", "dojo/request/notify", "dojo/dom-construct", "dojo/domReady!"],
				function(dom, request, notify, domConstruct){
					notify("load", function(data){
	                    domConstruct.place("<p>" + dom.byId('LoadingHint').innerHTML + "</p>","riskRanks");                      
	                }); 
					domConstruct.empty("riskRanks");                
	                // Request the text file
	                request.get("<%=request.getContextPath() %>/riskRank/list.action").then(
	                    function(response){
	                        // Display the text file content
	                        dom.byId('riskRanks').innerHTML = response;
	                    });
	                }
			);
			
			function refreshRiskRankList(){
				require(["dojo/dom", "dojo/on", "dojo/request", "dojo/request/notify", "dojo/dom-construct"],
					    function(dom, on, request, notify, domConstruct){
							notify("load", function(data){
			                    //i18n is required
			                    domConstruct.place("<p>" + dom.byId('LoadingHint').innerHTML + "</p>","riskRanks");
			                    
			                }); 
						    
							
							// Attach the onclick event handler to the textButton
					        //on(dom.byId("notifyTopics_refresh"), "click", function(evt){
					        	// prevent the page from navigating
	                      //      evt.stopPropagation();
	                       //     evt.preventDefault();
	                 
	                            domConstruct.empty("riskRanks");
					            // Request the text file
					            request.get("<%=request.getContextPath() %>/riskRank/list.action").then(
					            	function(response){
					                    // Display the text file content
					            		dom.byId('riskRanks').innerHTML = response;
					                });
					       // });
					    }
					);
			}
		 	
			require(["dojo/dom", "dojo/on", "dojo/request", "dojo/dom-form", "dojo/request/notify", "dojo/dom-construct", "dojo/domReady!"],
                    function(dom, on, request, domForm, notify, domConstruct){
                 
                        var form = dom.byId('save');
                        
                       notify("load", function(data){
                             //i18n is required
                             domConstruct.place("<p>" + dom.byId('LoadingHint').innerHTML + "</p>","riskRanks");
                             
                         });
                        
                         // Attach the onsubmit event handler of the form
                        on(form, "submit", function(evt){
                            // prevent the page from navigating after submit
                            evt.stopPropagation();
                            evt.preventDefault();
                 
                            domConstruct.empty("riskRanks");
                            // Post the data to the server
                            request.post("<%=request.getContextPath() %>/riskRank/save.action", {
                                // Send the username and password
                                data: domForm.toObject("save"),
                                // Wait 5 seconds for a response
                                timeout: 5000
                 
                            }).then(function(response){
                                dom.byId('riskRanks').innerHTML = response;
                                //domConstruct.place(response, 'riskRanks');
                                dom.byId("id").value = "";
                                dom.byId("rankType").value = "";
                                dom.byId("minValue").value = "";
                                dom.byId("maxValue").value = "";
                                dom.byId("version").value = "";
                            });
                        });
                    }
                );
			
			function removeRiskRank(removeRiskRankURL){
				require(["dojo/dom", "dojo/request", "dojo/request/notify", "dojo/dom-construct"],
                        function(dom,  request, notify, domConstruct) {
		                    notify("load", function(data){
		                        //i18n is required
		                        domConstruct.place("<p>" + dom.byId('LoadingHint').innerHTML + "</p>","riskRanks");                      
		                    }); 
		                    domConstruct.empty("riskRanks"); 
		                 // Request the text file
		                    request.del(removeRiskRankURL).then(
		                    		function(response){
                                        // Display the text file content
                                        dom.byId("riskRanks").innerHTML = response;
                                    });                                        
		               }
				);
			}
			
			function fillRiskRank(riskRankId){
				require(["dojo/dom", "dojo/string"],
                        function(dom, string) {
                            var id = riskRankId.split("_")[1];
                            var tr = dom.byId("row_"+id);
                            var tds = tr.getElementsByTagName("td");
                            dom.byId("id").value = id;
                            //alert(dom.textcontext(tds[0]));
                            dom.byId("rankType").value = string.trim(tds[0].innerHTML);
                            dom.byId("minValue").value = string.trim(tds[1].innerHTML);
                            dom.byId("maxValue").value = string.trim(tds[2].innerHTML);
                            dom.byId("version").value = string.trim(tds[3].innerHTML);   
                                                                
                       }
                );
			}
			 
		</script>
	</head>
	<body>
	
	<text id="LoadingHint" style="display:none"><s:property value="getText('Load')" /></text>
	    <s:url namespace="/riskRank" action="list" id="descrsUrl"/>

        <div class="roundedlist">
        	<div style="text-align: right;">
    			<a id="notifyTopics_refresh" href="javascript:void(0)" onclick="javascript:refreshRiskRankList()"><s:text name="refresh" /></a>
    		</div>
    		
    		<div id="riskRanks" >
    		  
    		</div>
        </div>

        <br/>

		<div class="roundedsave">
			<p><s:text name="riskRank.data" /></p>
			
            <table class="wwFormTable"><tr><td><form  id="save">
			    <s:textfield id="id" name="riskRank.id" cssStyle="display:none" />
				<s:textfield id="rankType" name="riskRank.rankType" key="riskRank.rankType"/>
				<s:textfield id="minValue" key="riskRank.minvalue" name="riskRank.minValue"/>
				<s:textfield id="maxValue" key="riskRank.maxvalue" name="riskRank.maxValue"/>
				<s:textfield id="version" name="riskRank.version" cssStyle="display:none"/>
				<s:submit id="submit" key="form.submit" loadingText="%{getText('Load')}"
        showLoadingText="true"/>
				<s:reset type="button" key="form.clear"/>
			</form></td></tr>
			</table>
      
		</div>
	</body>
</html>
