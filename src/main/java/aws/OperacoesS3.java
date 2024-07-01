package aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class OperacoesS3 {
    private final AmazonS3 clienteS3;

    public OperacoesS3(String accessKey, String secretKey) {
        var credenciais = new BasicAWSCredentials(accessKey, secretKey);

        // Configurar cliente S3
        clienteS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credenciais))
                .withRegion(Regions.SA_EAST_1).build();
    }


    public void criarBucket(String nomeBucket) {
        if (clienteS3.doesBucketExistV2(nomeBucket)) {
            return;
        }

        clienteS3.createBucket(nomeBucket);
    }

    public List<String> listarBuckets() {
        // retorna uma lista de nomes dos buckets
        return clienteS3.listBuckets().stream()
                .map(Bucket::getName)
                .collect(Collectors.toList());

    }

    public void deletarBucket(String nomeBucket) {
        if (!clienteS3.doesBucketExistV2(nomeBucket)) {
            return;
        }
        clienteS3.deleteBucket(nomeBucket);
    }

    public void enviarArquivo(String nomeBucket, String destinoArquivo, File origemArquivo) {
        if (!clienteS3.doesBucketExistV2(nomeBucket)) {
            return;
        }
        clienteS3.putObject(nomeBucket, destinoArquivo,origemArquivo);
    }

    public List<String> listarArquivos(String nomeBucket) {
        if (!clienteS3.doesBucketExistV2(nomeBucket)) {
        }
        var listaObjetos = clienteS3.listObjects(nomeBucket);
        return listaObjetos.getObjectSummaries()
                .stream() // Manipula coleções
                .map(S3ObjectSummary::getKey) // para cada sumario a gente pega a chave dele (id unico)
                .collect(Collectors.toList()); // faz uma lista de String
    }

    public void deletarArquivo (String nomeBucket, String chaveArquivo) {
        if (!clienteS3.doesBucketExistV2(nomeBucket)) {
            return;
        }
        clienteS3.deleteObject(nomeBucket, chaveArquivo);
    }



}
