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
angular.module('boxuppApp',['ui.codemirror','app','ngAnimate', 'ngLoadScript','ngRoute','ngResource','ui.ace','ngMessages']).
controller('boxuppAppController',function($scope,$http,$rootScope,$timeout,vagrantStatus,executeCommand,$q,$location,User){

	$scope.vagrantOutput = [{"type":"normal","output":"C:\\Users\\Paxcel Techn…second","dataEnd":false,"vagrantFileExists":true}];
	$scope.IntroOptions;
	$scope.IntroOptions = {
			steps:[
			       {
			    	   element: '#step1',
			    	   intro: "This is the dashboard. All the boxes, scripts and modules that you create, will be visible here. ",
			    	   position: 'bottom'
			       },
			       {
			    	   element: '#step2',
			    	   intro: "This is the main navigation bar.",
			    	   position: 'right'
			       },
			       {
			    	   element: '#step3',
			    	   intro: 'This is the Top Navigation Bar. It includes options to switch workspace and deploy any changes made to the project.',
			    	   position: 'bottom'
			       },
			       {
			    	   element: '#step4',
			    	   intro: 'Click here to manage boxes',
			    	   position: 'right'
			       },

			       {
			    	   element: '#step5',
			    	   intro: 'All the boxes that you create will be visible here',
			    	   position: 'right'
			       },
			       {
			    	   element: "#controlBarNav",
			    	   intro: 'This is the Control Bar. Add, edit or delete boxes, scripts and modules.',
			    	   position: 'top'
			       },
			       {
			    	   element: "#step7",
			    	   intro: 'You can either quickly create the box or create from scratch by providing various details.',
			    	   position: 'right'
			       },
			       {
			    	   element: "#step8",
			    	   intro: 'Create Scripts here',
			    	   position: 'right'
			       },
			       {
			    	   element: "#step9",
			    	   intro: 'Search for modules',
			    	   position: 'top'
			       },
			       {
			    	   element: "#step10",
			    	   intro: 'Click here to shell provision your machine.',
			    	   position: 'right'
			       },
			       {
			    	   element: "#step11",
			    	   intro: 'Choose Machine to provision',
			    	   position: 'right'
			       },
			       {
			    	   element: "#step12",
			    	   intro: 'Choose the script and click Apply.',
			    	   position: 'left'
			       },
			       {
			    	   element: "#step13",
			    	   intro: 'You can also Puppet Provision your machine',
			    	   position: 'right'
			       },
			       {
			    	   element: "#step14",
			    	   intro: 'Share on GitHub',
			    	   position: 'right'
			       },
			       {
			    	   element: "#step15",
			    	   intro: 'Click here to deploy all changes that you have made in the project',
			    	   position: 'bottom'
			       },
			       {
			    	   element: "#none",
			    	   intro: 'Thats all! Thank You!'
			       }

			       ],
			       showStepNumbers: false,
			       exitOnOverlayClick: true,
			       exitOnEsc:true,
			       nextLabel: '<strong>NEXT!</strong>',
			       prevLabel: '<span style="color:green">Previous</span>',
			       skipLabel: 'Skip',
			       doneLabel: 'Thanks'
	}

	$scope.ShouldAutoStart = function() {
		return false;
	}
	
	$scope.CompletedEvent = function (scope) {
	}

	$scope.ExitEvent = function (targetElement,scope) {
	}

	$scope.ChangeEvent = function (targetElement, scope) {
	}

	$scope.BeforeChangeEvent = function (targetElement, scope) {

		var nextStep = $(targetElement).attr('id');
		console.log(nextStep);
		switch(nextStep){
		case 'step1':
			$("#mainNavDashboard").click();
			break;
		case 'step4':
			$(targetElement).click();
			break;
		case 'step7':
			$rootScope.expandedCtrlBar = true;
			break;
		case 'step8':
			$("#step8").click();
			break;
		case 'step9':
			$(targetElement).click();
			break;
		case 'step10':
			$(targetElement).click();
			break;
		case 'step13':
			$(targetElement).click();
			break;
		}
	}

	$scope.AfterChangeEvent = function (targetElement, scope) {
	}

}).config(['$routeProvider','$httpProvider',
           function($routeProvider,$httpProvider) {

	$routeProvider.when('/login/',{
		templateUrl: 'templates/login.html',
		controller: 'loginController'
	}).when('/:userID/projects/', {
		templateUrl: 'templates/projects.html',
		controller: 'projectController',
		resolve: {
			success: function (User) {
				return User.checkSession();
			}
		}
	}).when('/projects/:userID/:projectID/:providerType/',{
		templateUrl: 'templates/projectInit.html',
		controller: 'projectInitController',
		resolve:{ 
			success: function (User) {
				return User.checkSession();
			}
		}
	}).when('/projects/:userID/:projectID/:providerType/docker/',{
		templateUrl: 'templates/dockerDashboard.html',
		controller: 'vboxController',
		resolve : {
			provider : function(){
				return 'docker';
			},
			success: function (User) {
				return User.checkSession();
			}
		}
	}).when('/projects/:userID/:projectID/:providerType/virtualbox/',{
		templateUrl: 'templates/vboxDashboard.html',
		controller: 'vboxController',
		resolve : {
			provider : function(){
				return 'virtualbox';
			},
			success: function (User) {
				return User.checkSession();
			}
		}
	}).otherwise({
		redirectTo : '/login/'
	});

	$httpProvider.interceptors.push(function ($q,$location) {
		return {
			'responseError': function (rejection) {
				if (rejection.status === 401) {
					$location.path('/login/');
				}
				return $q.reject(rejection);
			}
		};
	});

}
])