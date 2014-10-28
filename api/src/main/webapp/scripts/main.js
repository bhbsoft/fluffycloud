require.config({
	baseUrl : "scripts/",
	paths : {
		angular : "vendor/angular/angular",
		uiRouter : "vendor/uiRoute/angular-ui-router",
		angularBootstrap : "vendor/ui.bootstrap/ui-bootstrap-tpls-0.10.0",
		angularTouch : 'vendor/angular/angular-touch',
		jquery : "vendor/jquery/jquery-1.11.0",
		toaster : "vendor/toaster/toaster"
	},

	shim : {
		'angular' : {
			'exports' : 'angular'
		},
		'uiRouter' : {
			deps : [ 'angular' ]
		},
		'angularTouch' : {
			deps : [ 'angular' ]
		},
		'angularBootstrap' : {
			deps : [ 'angular', 'angularTouch' ]
		},
		'angularUIRouter' : {
			deps : [ 'angular' ]
		},
		'toaster' : {
			deps : [ 'angular' ]
		}
	},
	priority : [ "angular" ]
});

window.name = "NG_DEFER_BOOTSTRAP!";

require([ 'angular', 'app', 'uiRouter', 'angularTouch', 'angularBootstrap',
		'jquery', 'toaster', 'routes' ], function(angular, app, uiRouter,
		angularTouch, angularBootstrap, jquery, toaster, routes) {
	var $html = angular.element(document.getElementsByTagName('html')[0]);

	angular.element().ready(function() {
		angular.resumeBootstrap([ app['name'] ]);
		console.log("Running");
	});

});