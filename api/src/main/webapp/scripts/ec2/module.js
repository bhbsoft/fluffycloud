'use strict';

define([ 'angular', './controllers', './services' ], function(angular, controllers, services) {

	var module = angular.module('fluffyCloud.ec2', [ 'fluffyCloud.ec2.controllers', 'fluffyCloud.ec2.services' ]);

	return module;
});
