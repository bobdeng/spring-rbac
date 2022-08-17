// 统一的导出口
import HomeView from "./Home.vue";
import ConsoleView from "./Console.vue";
import UserConsoleView from './UserConsole.vue'
import TenantsView from "./tenant/Tenants.vue"
import TenantDomainView from './tenant/domain/ListTenantDomain.vue'
import TenantRoleView from './tenant/role/ListTenantRole.vue'
import UsersView from './tenant/user/ListTenantUser.vue'
import SetPasswordView from './tenant/user/UserPassword.vue'
import OrganizationView from './tenant/organazation/Organizations.vue'
import UserLoginView from './UserLogin.vue'
import ParametersView from './parameter/Parameters.vue'

export const Home = HomeView;
export const Console = ConsoleView
export const Tenants = TenantsView
export const Domain = TenantDomainView
export const Role = TenantRoleView

export const UserConsole = UserConsoleView
export const Users = UsersView
export const SetPassword = SetPasswordView
export const Organizations = OrganizationView
export const UserLogin = UserLoginView
export const Parameters = ParametersView






