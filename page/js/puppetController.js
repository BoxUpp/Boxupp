var boxuppApp = angular.module("boxuppApp");

boxuppApp.
		controller('puppetController',function($scope,$rootScope,retrieveMappings,$timeout,validator,fileUpload){
		alert('Hello, Puppet Controller has been called');

		$scope.readThisMessage = function(){

			alert('Hello world');
		}
		
		
		$scope.checkManifest = function(){
			$scope.outputConsole.boxuppExecuting = true;
			$scope.outputConsole.boxuppOutputWindow = true;
			
			validator.checkManifest($scope).then(function(response){
				if(response.exitCode === 1){
					$scope.flushVagrantOutputConsole();
					$scope.vagrantOutput.push({"type":"error","output":response.puppetErrorResponse});
					$scope.vagrantOutput.push({"type":"normal","output":response.puppetOutputResponse});
				}else{
					$scope.flushVagrantOutputConsole();
					$scope.vagrantOutput.push({"type":"normal","output":"Things look good !!"});
				}
			$scope.outputConsole.boxuppExecuting = false;
			});	
		}
		
		$scope.checkManifestVisibility = function(fileName){
			/*if(fileName === 'nodes.pp' || fileName === 'site.pp'){
				return false;
			}else{
				return true;
			}*/
			var restrictedFileNames = ['nodes.pp','site.pp'];
			if(restrictedFileNames.indexOf(fileName) > -1){
				return false;
			}
			return true;
		}
		
		$scope.$watch('puppet',function(newVal,oldVal){
			
			if(!angular.equals(newVal,oldVal)){
				$scope.setPuppetChangeFlag();
			}
			
		},true);
		
		$scope.setPuppetChangeFlag = function(){
			$scope.boxuppConfig.puppetChangeFlag = 1;
		
		}
		
		$rootScope.idCounter = 0;
		$scope.activeModule  = {}
		$scope.activeModuleFolder = {}
		$scope.activeModuleFile = null;
		$scope.fileIndex = 0;
		$scope.editedItem = null;
		$rootScope.fileSelected = {"value":"change"};
		
		$scope.$watch('selectedPuppetMaster',function(newVal,oldVal){
			if(typeof(newVal) !== 'undefined'){
				var newMasterIndex;
					for(var counter = 0; counter<$scope.boxesData.length; counter++){
						$scope.boxesData[counter].isPuppetMaster = false;
						if(_.isEqual(newVal,$scope.boxesData[counter])){
							newMasterIndex = counter;
						}
					}
				newVal.isPuppetMaster = true;
				var masterObj = $scope.boxesData.splice(newMasterIndex,1);
				$scope.boxesData.unshift(masterObj[0]);
			}
		},true);
		
		$scope.removeManifestFile = function(manifestFileIndex){
			var fileName = $scope.puppet.manifests[manifestFileIndex].moFileName;
			if($scope.activeModuleFile !== null){
				if(fileName === $scope.activeModuleFile.moFileName){
					$scope.resetActiveManifest();
				}
			}
			$scope.puppet.manifests.splice(manifestFileIndex,1);
		}
	
		$scope.selectModuleFile = function(fileClicked){
			if(!fileClicked.hasOwnProperty('preventModifications')){
				$scope.activeModuleFile = fileClicked;
			}
		}
		
		$scope.selectManifestFile = function(manifestFile){
			$scope.activeModuleFile = manifestFile;
		}
		
		$scope.addManifestsFolderFile = function(){
			$scope.puppet.manifests.splice(0,0,$scope.createNewManifestFile());
			$scope.selectManifestFile($scope.puppet.manifests[0]);
			$scope.startEditing($scope.puppet.manifests[0]);
		}
		$scope.changeEditorPositioning = function(){
			$scope.zoomEditor = !$scope.zoomEditor;
			$scope.outputConsole.boxuppOutputWindow = $scope.zoomEditor;			
		}
		
		$scope.removeModuleFile = function(moduleFilesArray,fileIndex){
			if($scope.activeModuleFile !== null){
				if(moduleFilesArray[fileIndex].moFileName === $scope.activeModuleFile.moFileName){
					$scope.resetActiveManifest();
				}
			}
			moduleFilesArray.splice(fileIndex,1);
		}
		
		$scope.addNewModule = function(){
				
				var newModule = {"moName":"","moFolders":[{"moFolderName":"manifests","moFolderFiles":[],"moFolders":[]},
														  {"moFolderName":"files","moFolderFiles":[],"moFolders":[]}]};
				$scope.puppet.modules.splice(0,0,newModule);
				$scope.startEditing($scope.puppet.modules[0]);
		}
		
		$scope.checkVisibility = function(folderName){
			return folderName === 'files'?true:false;
		}
		
		$scope.allowDeletion = function(folderName){
			return (folderName === 'files' || folderName === 'manifests')?true:false;
		}
		
		$scope.setUploadSettings = function(location,module){
			if(module !== null){
				//$scope.$parent.uploadFolder = module.moName;
				$scope.uploadLocation(module);
				fileUpload.changeDestination($scope,module.moName);
				$('#fileUploadModal').modal('show');
			}else{
				//$scope.$parent.uploadFolder = module.moName;
				$scope.uploadLocation($scope.puppet.files);
				fileUpload.changeDestination($scope,null);
				$('#fileUploadModal').modal('show');
			}
		}
		$rootScope.presentLocation = "";
		$scope.uploadLocation = function(module){
			$rootScope.presentLocation = module;
		}
		
		$scope.addNewModuleFolder = function(){
			return {"moFolderName":"","moFolderFiles":[],"moFolders":[]};	
		}
		
		$scope.createNewManifestFile = function(){
			return {"moFileName":"","moFileSource":""};
		}
			
		$scope.addModuleFolderFile = function(moduleFolder){
			moduleFolder.expanded = false;
			var newModuleFile = $scope.addNewModuleFile();
			moduleFolder.moFolderFiles.splice(0,0,newModuleFile);
			$scope.activeModuleFile = newModuleFile;
			$scope.startEditing(moduleFolder.moFolderFiles[0]);
		}
		
		$scope.editModuleName = function(module,moduleIndex){
			module.editModuleName = true;
			setTimeout(function(){
				$('div.moduleHeader').eq(moduleIndex).find('input.moduleNames').first().focus();
			
			},0);
		}
		
		$scope.expandModuleContents = function(moduleID,moduleExpandCaret){
			$('#'+moduleID).click();
			moduleExpandCaret = true;
		}
		$scope.moduleEditingDone = function(module){
			module.editModuleName = false;
		}
		
		
		
		$scope.defaultModuleFolders = ["manifests","files","templates","lib","tests","spec"];
		$scope.addChildFolder = function(moduleFolder){
			moduleFolder.expanded = false;
			moduleFolder.moFolders.splice(0,0,$scope.addNewModuleFolder());
			$scope.startEditing(moduleFolder.moFolders[0]);
		}
		$scope.addModuleFolder = function(module,moduleIndex){
			if(module.expanded === true){
				$('#'+module.moName).click();
				module.expanded = false;
			}
			module.moFolders.splice(0,0,$scope.addNewModuleFolder());
			$scope.startEditing(module.moFolders[0]);
		}
		
		$scope.addNewModuleFile = function(){
			return {"moFileName":"","moFileSource":" ","editFile":true};
		}
		$scope.checkModulesFolder = function(moduleClicked,folderClicked){
			
		}
		$scope.addNewInitFile = function(){
			return {"moFileName":"init.pp","moFileSource":""};
		}
		$scope.addNewModuleFolder = function(){
			return {"moFolderName":"","moFolderFiles":[],"moFolders":[]};	
		}
		$scope.addNewManifestsFolder = function(){
			var moduleFileArray = [];
			moduleFileArray.push($scope.addNewInitFile());
			return {"moFolderName":"manifests","moFolderFiles":moduleFileArray};
		}
		$scope.resetActiveManifest = function(){
			$scope.activeModuleFile = null;
		}
		
		$scope.removeModule = function(num){
			var userConfirmation = confirm("Are you sure you want to delete the module : "+$scope.puppet.modules[num].moName+" ?");
			if(userConfirmation){
				$scope.puppet.modules.splice(num,1);
			}
			$scope.resetActiveManifest();
			
		}
		$scope.removeModuleFolder = function(immediateParent, moduleParent, index){
			if(typeof(immediateParent) === 'undefined'){
				moduleParent.moFolders.splice(index,1);
			}else{
				immediateParent.moFolders.splice(index,1);		
			}
		}
		
		$scope.startEditing = function(item){
			if(!item.hasOwnProperty('preventModifications')){
				item.editName = true;
				$scope.editedItem = item;
			}
			
		}
		$scope.doneEditing = function(item){
			if(item.hasOwnProperty('moFileName')){
				if(item.moFileName.length === 0){
					return;
				}
			}
			else if(item.hasOwnProperty('moFolderName')){
				if(item.moFolderName.length === 0){
					return;
				}
			}
			else if(item.hasOwnProperty('moName')){
				if(item.moName.length === 0){
					return;
				}
			}
			item.editName = false;
			$scope.editedItem = null;
		}
		
}).factory('validator',function($http,$q,$timeout){
		return{
			checkManifest : function($scope){
				if($scope.zoomEditor){
					$scope.zoomEditor = false;
				}
				var mappings = {"manifestCode":$scope.activeModuleFile.moFileSource,"file":$scope.activeModuleFile.moFileName};
				var completeURL = "http://www.boxupp.com/boxupp-services/validate/checkPuppet";
				
				var deferred = $q.defer();
				$http({	
					method:'POST',
					headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
					url:completeURL,
					data:$.param(mappings)
				}).
				success(function(response, status, headers, config) {
					deferred.resolve(response);
				}).
				error(function(data, status, headers, config) {
						console.log(" : Error validating manifest file : ");
				});
				return deferred.promise;
			}
		}
	
	}).factory('fileUpload',function($http,$q,$timeout){
		return{
			changeDestination : function($scope,destination){
				var completeURL;
				if(destination !== null){
					completeURL = $scope.$parent.serverAddress + "/services/uploadHandler/destination?loc="+destination;
				}else{
					completeURL = $scope.$parent.serverAddress + "/services/uploadHandler/destination";
				}
				
				var deferred = $q.defer();
				$http({	
					method:'POST',
					headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
					url:completeURL,
				}).
				success(function(response, status, headers, config) {
					deferred.resolve(response);
				}).
				error(function(data, status, headers, config) {
						console.log(" : Error updating file location : ");
				});
				return deferred.promise;
			}
		}
	}).directive('ngDblClickFocus', function editFocus($timeout) {
	return function (scope, elem, attrs) {
		scope.$watch(attrs.ngDblClickFocus, function (newVal) {
			if (newVal) {
				$timeout(function () {
					elem[0].focus();
				}, 0, false);
			}
		});
	};
});
