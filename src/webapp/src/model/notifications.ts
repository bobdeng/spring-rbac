import {notification} from "ant-design-vue";

export const notifications = {
    error: (e: any) => {
        console.log(e)
        notification.error({message: "错误", description: e as string});
    }
}