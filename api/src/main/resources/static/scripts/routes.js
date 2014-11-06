define([ 'angular', 'app' ], function(angular, app) {

	'use strict';
	return app.config(function($stateProvider, $urlRouterProvider) {

		$urlRouterProvider.otherwise('/home');

		$stateProvider.state('vpcview', {
			url : '/vpc',
			templateUrl : 'views/vpcdetails.html'

		})
	})
});