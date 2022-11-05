# data source : 프로바이더에서 제공하는 리소스 정보를 가져와서 테라폼에서 사용할 수 있는 형태로 매핑시킬 수 있습니다
# ipv4.icanhazip ip를 가져옴

data "http" "workstation-external-ip" {
  url = "http://ipv4.icanhazip.com"
}

# 지역 값 선언
locals {
  workstation-external-cidr = "${chomp(data.http.workstation-external-ip.body)}/32"
}