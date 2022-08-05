import {describe, expect, test} from "vitest";
import {ajax} from "../../src/model/HttpServer";

describe("ajax test", () => {

    test("axios返回错误", async (当) => {
        try {
            const result = await ajax(() => Promise.resolve({data: "错误", status: 400}))
            assert.fail("")
        } catch (e) {
            expect(e).toEqual("错误");
        }
    })
    test("axios返回错误,无内容", async (当) => {
        try {
            await ajax(() => Promise.resolve({data: "", status: 400}))
            assert.fail("")
        } catch (e) {
            expect(e).toEqual(400);
        }
    })
    test("axios返回正常", async (当) => {
        const result = await ajax(() => Promise.resolve({data: {"result": 1}, status: 200}))
        expect(result).toEqual({"result": 1})
    })
})