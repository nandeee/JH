'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test3', {
                parent: 'entity',
                url: '/test3s',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test3.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test3/test3s.html',
                        controller: 'Test3Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test3');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test3.detail', {
                parent: 'entity',
                url: '/test3/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test3.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test3/test3-detail.html',
                        controller: 'Test3DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test3');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Test3', function($stateParams, Test3) {
                        return Test3.get({id : $stateParams.id});
                    }]
                }
            })
            .state('test3.new', {
                parent: 'test3',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test3/test3-dialog.html',
                        controller: 'Test3DialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {one: null, two: null, three: null, four: null, five: null, six: null, seven: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('test3', null, { reload: true });
                    }, function() {
                        $state.go('test3');
                    })
                }]
            })
            .state('test3.edit', {
                parent: 'test3',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test3/test3-dialog.html',
                        controller: 'Test3DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Test3', function(Test3) {
                                return Test3.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('test3', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
