/*******************************************************************************
 *  Copyright 2014 Paxcel Technologies
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *******************************************************************************/
angular.module("boxuppApp").controller('localGitController', [ '$scope', '$routeParams','remoteRepoFunctionality',function($scope, $routeParams, remoteRepoFunctionality){
	$scope.localGitConfig = {
		remoteRepoURI:"",
		password:"",
		repoBranch:"",
		path:"/local/Vagrantfile",
		comment:"Vagrantfile commit"
	};
	$scope.checkbranchList = false;
	$scope.buttonText = "Get Branches";
	$scope.statusText = "";
	//$scope.fetchRepoListSuccess = false;
	$scope.branchValid = true;
	
	$scope.resetFlags = function(){
		$scope.fetchBranchListSuccess = false;
		$scope.branchValid = false;
		
	}	

	
	$scope.getRemoteBranchList = function(){
		$scope.checkbranchList = true;
		$scope.localGitConfig.userID = $routeParams.userID;
		$scope.loaderValid = true;
		remoteRepoFunctionality.branchList($scope.localGitConfig.remoteRepoURI,$scope.localGitConfig.localRepoPath, $scope.localGitConfig.password).then(function(response){
			if(response != null){
				$scope.localGitConfig.gitBranchList = response;
				$scope.fetchBranchListSuccess = true;
				$scope.checkbranchList = false;
			}
		});

	}
		
	$scope.commitToRemoteRepo = function(){
		remoteRepoFunctionality.commitOnRemoteRepo($scope.localGitConfig, $routeParams.userID).then(function(err, response){
				if(err !== null){
					var responseObj = JSON.parse(err.request.response);
					alert(responseObj.message);
				}else{
					alert('Vagrant File has been successfully committed to your repository');
				}
			});
		}
}]).factory('remoteRepoFunctionality', function($http, $q){
	return{
		branchList: function(remoteRepoURI, localRepoPath, password){
			var repoURL = '/boxupp/resources/localGitRepo/getBranches';
			var parameters = {"remoteRepoURI" : remoteRepoURI, "localRepoPath":localRepoPath, "password" : password};
			var deferred = $q.defer();
			$http({
				method: 'GET',
				headers: {'Content-Type':'application/json; charset=UTF-8'},
				url : repoURL,
				params:parameters
			}).
			success(function (response,status, headers, config) {
				deferred.resolve(response);
				console.log(response);
			}).error(function(data, status, headers, config){
				console.log(":Error geting List of branches from remote server for a user");
			});
			return deferred.promise;
		},
		commitOnRemoteRepo: function(localGitConfig){
			var repoURL = '/boxupp/resources/localGitRepo/commmit';
			var deferred = $q.defer();
			$http({
				method: 'POST',
				url : repoURL,
				headers: {'Content-Type':'application/json; charset=UTF-8'},
				data : localGitConfig
			}).
			success(function (response,status, headers, config) {
				deferred.resolve(response);
				console.log(response);
			}).error(function(data, status, headers, config){
				console.log(":Error geting Repo from remote server");
			});
			return deferred.promise;
		}

	}
});
