import {Function} from "../../../model/HttpServer";

export class Functions {
    functions: Function[]

    constructor(functions: Function[]) {
        this.functions = functions;
    }

    allModules(): string[] {
        const modules = [] as string[]
        this.readModules(modules, this.functions)
        return modules;
    }

    readModules(result: string[], functions: Function[]) {
        functions.forEach((fun: Function) => {
            if (fun.children) {
                result.push(fun.key)
                this.readModules(result, fun.children)
            }
        })
    }
}