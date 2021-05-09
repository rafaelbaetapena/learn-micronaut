## Micronaut 2.5.1 Documentation

- [User Guide](https://docs.micronaut.io/2.5.1/guide/index.html)
- [API Reference](https://docs.micronaut.io/2.5.1/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/2.5.1/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

## Feature elasticsearch documentation

- [Micronaut Elasticsearch Driver documentation](https://micronaut-projects.github.io/micronaut-elasticsearch/latest/guide/index.html)

### Local Elastic Search

https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html

Single node cluster with exposed port 9200.
`docker run --name mn-elastic-search -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.10.1`