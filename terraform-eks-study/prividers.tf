# providers "aws" : azure, aws, gcp 같은 클라우스 사업자를 칭한다

provider "aws" {
  region = "ap-northeast-2"
}

data "aws_availability_zones" "available" {
  exclude_names = ["ap-northeast-2b", "ap-northeast-2d"]
}

# provider "http" : 일반 HTTP 서버와 상호 작용하기 위한 유틸리티 공급자
provider "http" {}
