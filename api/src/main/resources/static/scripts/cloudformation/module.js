'use strict';

define([ 'angular', './controllers', './services', './directives', './cfConstants' ], function(angular, controllers,
		services, directives, cfConstants) {

	var module = angular.module('fluffyCloud.cf', [ 'fluffyCloud.cf.controllers', 'fluffyCloud.cf.services',
			'fluffyCloud.cf.directives' ]);
	module.value("CFCONSTANTS", cfConstants);
	return module;
});
