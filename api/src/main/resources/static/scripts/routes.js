define([ 'angular', 'app' ], function(angular, app) {

	'use strict';
	return app.config(function($stateProvider, $urlRouterProvider) {

		$urlRouterProvider.otherwise('/home');

		$stateProvider.state('home', {
			url : '/home',
			views : {
				'' : {
					templateUrl : 'views/dashboard.html'
				},
				'header@home' : {
					templateUrl : 'views/header.html'
				},
				'righttemplate@home' : {
					templateUrl : 'views/rightTemplate.html'
				},
				'maintemplate@home' : {
					templateUrl : 'views/vpcList.html',
					controller : 'EC2CTRL'
				},
				'lefttemplate@home' : {
					templateUrl : 'views/leftTemplate.html'
				},
				'footer@home' : {
					templateUrl : 'views/footer.html'
				}
			}
		}).state('home.vpc', {
			url : '/vpc',
			views : {
				'instancedetails@home' : {
					templateUrl : 'views/vpcdetails.html',
					controller : 'INSTANCECTRL'
				}
			}
		}).state('home.vpc.summary', {
			url : '/sg',
			views : {
				'summarysection' : {
					templateUrl : 'views/sgsummary.html'
				}
			}
		});
	})
});