# selenium으로 정적인 데이터를 동적인 데이터로 받아 올 수 있다.
# ex_도메인은 같은데 다른정보를 보여주는 페이지의 경우 도메인만 따오면 그 한 화면만 보인다
# ajax


from selenium import webdriver
import time


chrome = webdriver.Chrome('./chrome/chromedriver')
chrome.get('https://www.opinet.co.kr/searRgSelect.do')

gu_list_raw = chrome.find_element_by_xpath("""//*[@id="SIGUNGU_NM0"]""") #"""인 이유는 중간에 문자형식"들이 나와서.
gu_list = gu_list_raw.find_elements_by_tag_name('option')

gu_names = [option.get_attribute("value") for option in gu_list]
print(gu_names)
gu_names.remove('')
print(gu_names)


for gu in gu_names:
    elem_gu = chrome.find_element_by_id("SIGUNGU_NM0")
    elem_gu.send_keys(gu)
    time.sleep(1.5)
    elem_excel = chrome.find_element_by_xpath("""//*[@id="glopopd_excel"]/span""")
    elem_excel.click()

chrome.close()















