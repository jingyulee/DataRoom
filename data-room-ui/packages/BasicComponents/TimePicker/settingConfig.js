
import { commonConfig, displayOption } from 'data-room-ui/js/config'

export const settingConfig = {
  // text内容
  text: '时间选择器',
  // 设置面板属性的显隐
  displayOption: {
    ...displayOption,
    dataAllocation: { enable: true },
    dataSourceType: { enable: false },
    params: { enable: false }
  }
}

const customConfig = {
  type: 'timePicker',
  // 名称
  title: '时间选择器',
  root: {
    version: '2023071001'
  },
  // 自定义属性
  customize: {
    value: '',
    // 选择框背景颜色
    backgroundColor: '#35393F',
    // 选择框文字颜色
    fontColor: '#FFFFFF',
    // 选择框文字大小
    fontSize: 20,
    // 下拉框背景颜色
    dropDownBackgroundColor: '#35393F',
    // 下拉框字体颜色
    dropDownFontColor: '#FFFFFF',
    // 下拉项hover背景颜色
    dropDownHoverBackgroundColor: '#6A7E9D',
    // 下拉项hover字体颜色
    dropDownHoverFontColor: '#FFFFFF',
    // 下拉项激活文字颜色
    dropDownSelectedFontColor: '#409EFF',
    // 时间格式化类型:时间戳（timestamp），自定义（custom）
    formatType: 'custom',
    // 绑定值的格式
    valueFormat: 'HH:mm:ss'
  }
}
export const dataConfig = {
  ...commonConfig(customConfig)
}
