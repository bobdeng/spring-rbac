// ***********************************************************
// This example support/component.ts is processed and
// loaded automatically before your test files.
//
// This is a great place to put global configuration and
// behavior that modifies Cypress.
//
// You can change the location of this file or turn off
// automatically serving support files with the
// 'supportFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/configuration
// ***********************************************************

// Import commands.js using ES2015 syntax:
import './commands'

// Alternatively you can use CommonJS syntax:
// require('./commands')

import {mount} from 'cypress/vue'

// Augment the Cypress namespace to include type definitions for
// your custom command.
// Alternatively, can be defined in cypress/support/component.d.ts
// with a <reference path="./component" /> at the top of your spec.
type MountParams = Parameters<typeof mount>
type OptionsParam = MountParams[1] & { router?: Router }
declare global {
    namespace Cypress {
        interface Chainable {
            mount(component: any, options?: OptionsParam): Chainable<any>
        }
    }
}

import {createMemoryHistory, createRouter, Router} from 'vue-router'
import {routes} from '../../src/router'

Cypress.Commands.add('mount', (component, options = {}) => {
    // Setup options object
    options.global = options.global || {}
    options.global.plugins = options.global.plugins || []

    if (!options.router) {
        options.router = createRouter({
            routes: routes,
            history: createMemoryHistory(),
        })
    }

    // Add router plugin
    options.global.plugins.push({
        install(app:any) {
            app.use(options.router)
        },
    })

    return mount(component, options)
})

// Example use:
// cy.mount(MyComponent)