'use strict';

define([ 'angular' ], function(angular) {

	var module = angular.module('fluffyCloud.ec2.directives', []);

	module.directive('ngLoading', [ '$compile', function($compile) {
		return {
			restrict : 'A',
			scope : false,
			link : function(scope, element, attrs) {
				var initialContents = element.html();
				scope.$watch(attrs.ngLoading, function(loading) {
					if (loading == true) {
						element.html('<div class="loader"></div>');
					} else {
						element.html(initialContents);
						$compile(element.contents())(scope);
					}
				})
			}
		}

	} ]);

	module.directive('loader', function() {
		return {
			restrict : 'AE',
			replace : 'true',
			transclude : 'true',
			templateUrl : 'views/loader.html'
		};
	});

	module.directive('slideToggle', function() {
		return {
			restrict : 'A',
			scope : {
				isOpen : "=slideToggle" // 'data-slide-toggle' in our
			// html
			},
			link : function(scope, element, attr) {
				var slideDuration = parseInt(attr.slideToggleDuration, 10) || 200;

				// Watch for when the value bound to isOpen changes
				// When it changes trigger a slideToggle
				scope.$watch('isOpen', function(newIsOpenVal, oldIsOpenVal) {
					if (newIsOpenVal !== oldIsOpenVal) {
						element.stop().slideToggle(slideDuration);
					}
				});

			}
		};
	});
	
	

	// Implementing the withErrors directive
	// The withErrors directive controller allows fielderror controllers to
	// register themselves with.
	// It also registers itself with the setFormErrors service so additional
	// errors can be set without Angular validations.
	// Lastly, it provides two methods for the input directive to call whenever
	// it encounters an Angular validation error.


	module.directive('withErrors', ['setFormErrors', function (setFormErrors) {
	    return {
	        restrict: 'A',
	        require: ['withErrors'],
	        controller: ['$scope', '$element', function ($scope, $element) {
	            var controls = {};

	            this.addControl = function (fieldName, ctrl) {
	                controls[fieldName] = ctrl;
	            };

	            this.setErrorsFor = function (fieldName, errors) {
	                if (!(fieldName in controls)) return;
	                return controls[fieldName].setErrors(errors);
	            };

	            this.clearErrorsFor = function (fieldName, errors) {
	                if (!(fieldName in controls)) return;
	                return controls[fieldName].clearErrors(errors);
	            };
	        }],
	        link: function (scope, element, attrs, ctrl) {
	            // Make this form controller accessible to setFormErrors service
	            setFormErrors._register(attrs.name, ctrl);
	        }
	    };
	}]);


	// Implementing the input directive
	// The input directive requires the ngModel and withErrors directive
	// controllers.
	// If they are both present, then it will listen for any errors on ngModel,
	// map those errors to messages, and set those messages using the withErrors
	// controller.
	module.directive('input', function () {
	    return {
	        restrict: 'E',
	        require: ['?ngModel', '?^withErrors'],
	        // scope: true,
	        link: function (scope, element, attrs, ctrls) {
	            var ngModelCtrl = ctrls[0];
	            var withErrorsCtrl = ctrls[1];
	            var fieldName = attrs.name;

	            if (!ngModelCtrl || !withErrorsCtrl) return;

	            // Watch for model changes and set errors if any
	            scope.$watch(attrs.ngModel, function () {
	                if (ngModelCtrl.$dirty && ngModelCtrl.$invalid) {
	                    withErrorsCtrl.setErrorsFor(fieldName, errorMessagesFor(ngModelCtrl));
	                } else if (ngModelCtrl.$valid) {
	                    withErrorsCtrl.clearErrorsFor(fieldName);
	                }
	            });

	            // Mapping Angular validation errors to a message
	            var errorMessages = {
	                required: 'This field is required',
	                pattern: 'This field does not match pattern',
	                minlength: 'This field is too short',
	                maxlength: 'This field is too long',
	                email: 'This is not a valid E-mail id',
	                mobile: 'This is not a valid Mobile number',
	                min: 'Value less than the minimum allowed',
	                max: 'Value greater than the maximum allowed'
	                // etc.
	            };

	            function errorMessagesFor(ngModelCtrl) {
	                return Object.keys(ngModelCtrl.$error).
	                  map(function (key) {
	                      if (ngModelCtrl.$error[key]) return errorMessages[key];
	                      else return null;
	                  }).
	                  filter(function (msg) {
	                      return msg !== null;
	                  });
	            }
	        }
	    }
	});


	// Implementing the fielderrors directive
	// Finally, the fielderrors directive requires a parent withErrors directive
	// controller which it registers itself with.
	// It also provides methods for setting and clearing errors.
	module.directive('fielderrors', function () {
	    return {
	        restrict: 'E',
	        replace: true,
	        scope: true,
	        require: ['fielderrors', '^withErrors'],
	        template:
	          '<div class="text-danger text-right pull-right"><div ng-repeat="error in errors">' +
	            '<small class="error">{{error}}</small>' +
	          '</div></div>',
	        controller: ['$scope', function ($scope) {
	            $scope.errors = [];
	            this.setErrors = function (errors) {
	                $scope.errors = errors;
	            };
	            this.clearErrors = function () {
	                $scope.errors = [];
	            };
	        }],
	        link: function (scope, element, attrs, ctrls) {
	            var fieldErrorsCtrl = ctrls[0];
	            var withErrorsCtrl = ctrls[1];
	            withErrorsCtrl.addControl(attrs.for, fieldErrorsCtrl);
	        }
	    };
	});


	// Implementing the setFormErrors service
	// We start with our setFormErrors service, which allows the withErrors
	// directive to register themselves with.
	// The exposed service function will set field errors for a given form name.
	module.factory('setFormErrors', function () {
	    // Registered withErrors controllers
	    var withErrorCtrls = {};

	    // The exposed service
	    var setFormErrors = function (opts) {
	        var fieldErrors = opts.fieldErrors;
	        var ctrl = withErrorCtrls[opts.formName];

	        Object.keys(fieldErrors).forEach(function (fieldName) {
	            ctrl.setErrorsFor(fieldName, fieldErrors[fieldName]);
	        });
	    };

	    // Registers withErrors controller by form name (for internal use)
	    setFormErrors._register = function (formName, ctrl) {
	        withErrorCtrls[formName] = ctrl;
	    };

	    return setFormErrors;
	});

	return module;
});