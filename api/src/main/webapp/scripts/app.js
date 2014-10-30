'use strict';

define([ 'angular', 'uiRouter', 'angularBootstrap', './ec2/module'], function(
		angular, uiRouter, uibootstrap, ec2) {

	return angular.module('fluffyCloud', [ 'ui.router', 'ui.bootstrap', 'fluffyCloud.ec2' ]);
});
