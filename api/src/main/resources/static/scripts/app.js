'use strict';

define([ 'angular', 'angularAnimate', 'uiRouter', 'angularBootstrap',
		'toaster', './ec2/module' ], function(angular, angularAnimate,
		uiRouter, uibootstrap, toaster, ec2) {

	return angular.module('fluffyCloud', [ 'ui.router', 'ui.bootstrap',
			'toaster', 'fluffyCloud.ec2', 'ngAnimate' ]);
});
