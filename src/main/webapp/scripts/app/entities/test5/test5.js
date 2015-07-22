'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test5', {
                parent: 'entity',
                url: '/test5s',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test5.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test5/test5s.html',
                        controller: 'Test5Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test5');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test5.detail', {
                parent: 'entity',
                url: '/test5/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test5.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test5/test5-detail.html',
                        controller: 'Test5DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test5');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Test5', function($stateParams, Test5) {
                        return Test5.get({id : $stateParams.id});
                    }]
                }
            })
            .state('test5.new', {
                parent: 'test5',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test5/test5-dialog.html',
                        controller: 'Test5DialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, age: null, date: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('test5', null, { reload: true });
                    }, function() {
                        $state.go('test5');
                    })
                }]
            })
            .state('test5.edit', {
                parent: 'test5',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test5/test5-dialog.html',
                        controller: 'Test5DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Test5', function(Test5) {
                                return Test5.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('test5', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
