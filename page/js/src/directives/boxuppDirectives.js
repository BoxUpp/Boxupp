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
angular.module('boxuppApp').directive('customScroll',[function(){
	
	function link(scope, element, attributes){		
		element.perfectScrollbar({
			  wheelSpeed: 20,
			  wheelPropagation: true,
			  minScrollbarLength: 20
		});
	}

	return {
		link : link
	}

}]);

angular.module('boxuppApp').directive('ngIntroOptions', ['$timeout', function ($timeout) {

    return {
        restrict: 'A',
        scope: {
            ngIntroMethod: "=",
            ngIntroExitMethod: "=?",
            ngIntroOptions: '=',
            ngIntroOncomplete: '=',
            ngIntroOnexit: '=',
            ngIntroOnchange: '=',
            ngIntroOnbeforechange: '=',
            ngIntroOnafterchange: '=',
            ngIntroAutostart: '&',
            ngIntroAutorefresh: '='
        },
        link: function(scope, element, attrs) {

            var intro;

            scope.ngIntroMethod = function(step) {

                var navigationWatch = scope.$on('$locationChangeStart', function(){
                  intro.exit();
                });

                if (typeof(step) === 'string') {
                    intro = introJs(step);

                } else {
                    intro = introJs();
                }

                intro.setOptions(scope.ngIntroOptions);
                
                if (scope.ngIntroAutorefresh) {
                  scope.$watch(function(){
                    intro.refresh();
                  });
                }
                
                if (scope.ngIntroOncomplete) {
                    intro.oncomplete(function() {
                        scope.ngIntroOncomplete.call(this, scope);
                        $timeout(function() {scope.$digest()});
                        navigationWatch();
                    });
                }

                if (scope.ngIntroOnexit) {
                    intro.onexit(function() {
                        scope.ngIntroOnexit.call(this, scope);
                        $timeout(function() {scope.$digest()});
                        navigationWatch();
                    });
                }

                if (scope.ngIntroOnchange) {
                    intro.onchange(function(targetElement){
                        scope.ngIntroOnchange.call(this, targetElement, scope);
                        $timeout(function() {scope.$digest()});
                    });
                }

                if (scope.ngIntroOnbeforechange) {
                    intro.onbeforechange(function(targetElement) {
                        scope.ngIntroOnbeforechange.call(this, targetElement, scope);
                        $timeout(function() {scope.$digest()});
                    });
                }

                if (scope.ngIntroOnafterchange) {
                    intro.onafterchange(function(targetElement){
                        scope.ngIntroOnafterchange.call(this, targetElement, scope);
                        $timeout(function() {scope.$digest()});
                    });
                }

                if (typeof(step) === 'number') {
                    intro.goToStep(step).start();
                } else {
                    intro.start();
                }
            };

            scope.ngIntroExitMethod = function (callback) {
                intro.exit();
                callback();
            };

            if (scope.ngIntroAutostart()) {
                $timeout(function() {
                    scope.ngIntroMethod();
                });
            }
        }
    };
}]);